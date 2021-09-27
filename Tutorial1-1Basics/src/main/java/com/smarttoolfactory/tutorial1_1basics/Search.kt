package com.smarttoolfactory.tutorial1_1basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.tutorial1_1basics.model.TutorialSectionModel

@Composable
fun SearchBar( modifier: Modifier = Modifier,onBack: (() -> Unit)? = null) {

    var searchQuery by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        color = Color(0xffF5F5F5),
        shape = RoundedCornerShape(percent = 50),
    ) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Box(
                contentAlignment =Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxSize()
            ) {

                if (searchQuery.isEmpty()) {
                    SearchHint(modifier.padding( start = 24.dp, end = 8.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding( top = 9.dp, bottom= 8.dp, start = 24.dp, end = 8.dp),
                        value = searchQuery,
                        onValueChange = { newValue ->
                            searchQuery = newValue
                        },
                        singleLine = true
                    )

                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Filled.Cancel, contentDescription = null)
                        }
                    } else {
//                        Spacer(Modifier.width(IconSize))
                    }

                }
            }
        }

    }
}


private val IconSize = 48.dp

@Composable
private fun SearchHint(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)

    ) {
        Text(
            color = Color(0xff757575),
            text = "Search a Tag or Description",
        )
    }
}

@Stable
class SearchState(
    query: TextFieldValue,
    focused: Boolean,
    searching: Boolean,
    suggestions: List<TutorialSectionModel>,
    searchResults: List<TutorialSectionModel>
) {
    var query by mutableStateOf(query)
    var focused by mutableStateOf(focused)
    var searching by mutableStateOf(searching)
    var suggestions by mutableStateOf(suggestions)
    var searchResults by mutableStateOf(searchResults)
    val searchDisplay: SearchDisplay
        get() = when {
            !focused && query.text.isEmpty() -> SearchDisplay.InitialResults
            focused && query.text.isEmpty() -> SearchDisplay.Suggestions
            searchResults.isEmpty() -> SearchDisplay.NoResults
            else -> SearchDisplay.Results
        }
}

/**
 * Enum class with different values to set search state based on text, focus, initial state and
 * results from search.
 *
 * **InitialResults** represents the initial state before search is initiated. This represents
 * the whole screen
 *
 */
enum class SearchDisplay {
    InitialResults, Suggestions, Results, NoResults
}

