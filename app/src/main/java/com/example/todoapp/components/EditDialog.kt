package com.example.todoapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.MainViewModel

@Composable
fun EditDialog(
    viewModel: MainViewModel = hiltViewModel()
) {
    // コンポーザブル(AlertDialog)がUIから削除されるタイミングでonDisposeの中に記載された処理が走る
    // 引数にはkeyとeffectがあるが、今回はこれが走る際に一回だけセットすればいいから。
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetProperties()
        }
    }

    AlertDialog(
        //画面の外側をタップしたとき
        onDismissRequest = { viewModel.isShowDialog = false },
        title = {
            Text(
                text =
                if (viewModel.idEditing) "タスク更新"
                else "タスク新規作成"
            )
        },
        text = {
            Column {
                Text(text = "タイトル")
                // valueで指定した値をテキストフィールドにonValueChangeで設定する
                TextField(value = viewModel.title,
                    onValueChange = { viewModel.title = it })
                Text(text = "詳細")
                TextField(value = viewModel.description,
                    onValueChange = { viewModel.description = it })

            }
        }, buttons = {
            Row(modifier = Modifier.padding(8.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier.width(120.dp),
                    onClick = { viewModel.isShowDialog = false },


                    ) {
                    Text(text = "キャンセル")

                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(modifier = Modifier.width(120.dp),
                    onClick = {
                        viewModel.isShowDialog = false
                        if (viewModel.idEditing) {
                            viewModel.updateTask()

                        } else {
                            viewModel.createTask()
                        }

                    }) {
                    Text(text = "OK")
                }


            }


        })
}