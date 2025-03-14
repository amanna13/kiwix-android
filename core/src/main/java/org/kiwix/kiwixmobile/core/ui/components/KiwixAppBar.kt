/*
 * Kiwix Android
 * Copyright (c) 2025 Kiwix <android.kiwix.org>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.kiwix.kiwixmobile.core.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import org.kiwix.kiwixmobile.core.ui.models.ActionMenuItem
import org.kiwix.kiwixmobile.core.ui.models.IconItem
import org.kiwix.kiwixmobile.core.ui.theme.Black
import org.kiwix.kiwixmobile.core.ui.theme.KiwixTheme
import org.kiwix.kiwixmobile.core.ui.theme.MineShaftGray350
import org.kiwix.kiwixmobile.core.ui.theme.White
import org.kiwix.kiwixmobile.core.utils.ComposeDimens.KIWIX_APP_BAR_HEIGHT
import org.kiwix.kiwixmobile.core.utils.ComposeDimens.SIXTEEN_DP

const val TOOLBAR_TITLE_TESTING_TAG = "toolbarTitle"

@Composable
fun KiwixAppBar(
  @StringRes titleId: Int,
  navigationIcon: @Composable () -> Unit,
  actionMenuItems: List<ActionMenuItem> = emptyList(),
  // Optional search bar, used in fragments that require it
  searchBar: (@Composable () -> Unit)? = null
) {
  KiwixTheme {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(KIWIX_APP_BAR_HEIGHT)
        .background(color = Black),
      verticalAlignment = Alignment.CenterVertically
    ) {
      navigationIcon()
      searchBar?.let {
        // Display the search bar when provided
        it()
      } ?: run {
        // Otherwise, show the title
        AppBarTitle(titleId)
      }
      Spacer(Modifier.weight(1f))
      ActionMenu(actionMenuItems)
    }
  }
}

@Composable
private fun AppBarTitle(
  @StringRes titleId: Int
) {
  val appBarTitleColor = if (isSystemInDarkTheme()) {
    MineShaftGray350
  } else {
    White
  }
  Text(
    text = stringResource(titleId),
    color = appBarTitleColor,
    style = MaterialTheme.typography.titleLarge.copy(fontWeight = SemiBold),
    modifier = Modifier
      .padding(horizontal = SIXTEEN_DP)
      .testTag(TOOLBAR_TITLE_TESTING_TAG)
  )
}

@Composable
private fun ActionMenu(actionMenuItems: List<ActionMenuItem>) {
  Row {
    actionMenuItems.forEach { menuItem ->
      IconButton(
        enabled = menuItem.isEnabled,
        onClick = menuItem.onClick,
        modifier = Modifier.testTag(menuItem.testingTag)
      ) {
        Icon(
          painter = when (val icon = menuItem.icon) {
            is IconItem.Vector -> rememberVectorPainter(icon.imageVector)
            is IconItem.Drawable -> painterResource(icon.drawableRes)
          },
          contentDescription = stringResource(menuItem.contentDescription),
          tint = if (menuItem.isEnabled) menuItem.iconTint else Color.Gray
        )
      }
    }
  }
}
