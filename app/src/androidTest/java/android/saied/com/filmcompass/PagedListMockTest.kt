package android.saied.com.filmcompass

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.rules.TestRule

class PagedListMockTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun mockPagedListWorks() {
        val list = listOf(1, 2, 3, 7)
        val pagedList = list.asPagedList()

        assertEquals(list, pagedList?.toList())
    }
}