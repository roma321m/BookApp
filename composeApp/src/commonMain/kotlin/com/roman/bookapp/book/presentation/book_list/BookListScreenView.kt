package com.roman.bookapp.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookapp.composeapp.generated.resources.Res
import bookapp.composeapp.generated.resources.no_favorite_books
import bookapp.composeapp.generated.resources.no_search_results
import bookapp.composeapp.generated.resources.tab_title_favorites
import bookapp.composeapp.generated.resources.tab_title_search
import com.roman.bookapp.book.domain.Book
import com.roman.bookapp.book.presentation.book_list.components.BookListView
import com.roman.bookapp.book.presentation.book_list.components.BookSearchBarView
import com.roman.bookapp.core.presentation.DarkBlue
import com.roman.bookapp.core.presentation.DesertWhite
import com.roman.bookapp.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()

    BookListScreenView(
        uiState = uiState.value,
        onAction = {
            when (it) {
                is BookListAction.OnBookClick -> onBookClick(it.book)
                else -> Unit
            }
            viewModel.onAction(it)
        },
        modifier = modifier,
    )
}


@Composable
fun BookListScreenView(
    uiState: BookListState,
    onAction: (BookListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState(
        initialPage = uiState.selectedTabIndex,
        pageCount = { 2 }
    )
    val searchResultListState = rememberLazyListState()
    val favoriteBooksListState = rememberLazyListState()

    LaunchedEffect(uiState.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }

    LaunchedEffect(uiState.selectedTabIndex) {
        pagerState.animateScrollToPage(uiState.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onAction(BookListAction.OnTabSelected(page))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBarView(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp),
            searchQuery = uiState.searchQuery,
            onSearchQueryChange = {
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            }
        )
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(
                topStart = 32.dp,
                topEnd = 32.dp,
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BookListTabRowView(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(700.dp)
                        .fillMaxWidth(),
                    selectedTabIndex = uiState.selectedTabIndex,
                    onSelectedTabIndexChange = {
                        onAction(BookListAction.OnTabSelected(it))
                    }
                )

                Spacer(Modifier.height(4.dp))

                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    state = pagerState
                ) { pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when(pageIndex) {
                            0 -> {
                                if (uiState.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        uiState.errorMessages != null -> {
                                            Text(
                                                text = uiState.errorMessages.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        uiState.searchResult.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                            )
                                        }
                                        else -> {
                                            BookListView(
                                                modifier = Modifier.fillMaxSize(),
                                                books = uiState.searchResult,
                                                onBookClick = {
                                                    onAction(BookListAction.OnBookClick(it))
                                                },
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }
                                }
                            }
                            1 -> {
                                if (uiState.favoriteBooks.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorite_books),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                } else {
                                    BookListView(
                                        modifier = Modifier.fillMaxSize(),
                                        books = uiState.favoriteBooks,
                                        onBookClick = {
                                            onAction(BookListAction.OnBookClick(it))
                                        },
                                        scrollState = favoriteBooksListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookListTabRowView(
    selectedTabIndex: Int,
    onSelectedTabIndexChange: (Int) -> Unit,
    modifier: Modifier
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        containerColor = DesertWhite,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(it[selectedTabIndex]),
                color = SandYellow,
            )
        }
    ) {
        Tab(
            modifier = Modifier,
            selected = selectedTabIndex == 0,
            onClick = {
                onSelectedTabIndexChange(0)
            },
            selectedContentColor = SandYellow,
            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = stringResource(Res.string.tab_title_search)
            )
        }
        Tab(
            modifier = Modifier,
            selected = selectedTabIndex == 1,
            onClick = {
                onSelectedTabIndexChange(1)
            },
            selectedContentColor = SandYellow,
            unselectedContentColor = Color.Black.copy(alpha = 0.5f)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = stringResource(Res.string.tab_title_favorites)
            )
        }
    }
}