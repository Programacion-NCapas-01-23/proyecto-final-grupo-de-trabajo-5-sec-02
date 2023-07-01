@file:OptIn(ExperimentalFoundationApi::class)

package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Tab
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults as TabRowDefaults3
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.models.TabItem
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomTabRow(
    tabItems: List<TabItem>,
    scope: CoroutineScope,
    pagerState: PagerState) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally) {
            
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPosition ->
                    TabRowDefaults3.Indicator(
                        height = 2.dp,
                        color = MaterialTheme3.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .tabIndicatorOffset(tabPosition[pagerState.currentPage])
                            .width(4.dp)
                    )
                },
                divider = {},
                containerColor = MaterialTheme3.colorScheme.primaryContainer
            ) {
                tabItems.forEachIndexed { index, item ->
                    CustomTab(
                        selected = remember { mutableStateOf(pagerState.currentPage == index)},
                        icon = item.icon,
                        title = item.title,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )

                }
            }
            
            HorizontalPager(
                pageCount = tabItems.size,
                state = pagerState
            ) {
                tabItems[pagerState.currentPage].screen()
            }
        }
    }
}

@Preview
@Composable
fun TabRowPreview () {

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val tabItems = listOf<TabItem>(
        TabItem.Percentages,
        TabItem.Grades
    )

    UTrackerTheme() {
        CustomTabRow( tabItems,  scope, pagerState)
    }
}