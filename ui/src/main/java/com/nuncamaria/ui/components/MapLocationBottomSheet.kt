package com.nuncamaria.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapLocationBottomSheet(goNext: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(true) }

    Column {
        HorizontalDivider(thickness = 2.dp, color = com.nuncamaria.ui.theme.AppColor.Primary)

        Box(
            modifier = Modifier
                .background(com.nuncamaria.ui.theme.AppColor.Background)
                .padding(com.nuncamaria.ui.theme.Spacing.md)
        ) {
            FloatingButton(
                icon = Icons.Default.LocationSearching,
                label = "Añade el punto de la incidencia",
                maxWith = true
            ) {
                showBottomSheet = true
            }
        }

        Spacer(modifier = Modifier.height(com.nuncamaria.ui.theme.Spacing.lg))
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = com.nuncamaria.ui.theme.AppColor.Background
        ) {

            Column(
                modifier = Modifier.padding(horizontal = com.nuncamaria.ui.theme.Spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(com.nuncamaria.ui.theme.Spacing.md)
            ) {

                Text(text = "¿Problemas encontrando la calle? Prueba ingresándola a mano.")


                //    SearchBar(query = "Address", onQueryChange = {}, onSearch = {}, active = true, onActiveChange = {}) {

                //    }

                FloatingButton(
                    icon = Icons.Default.PhotoCamera,
                    label = "Continue",
                    maxWith = true
                ) {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }

                    goNext()
                }
            }

            Spacer(modifier = Modifier.height(com.nuncamaria.ui.theme.Spacing.lg))
        }
    }
}