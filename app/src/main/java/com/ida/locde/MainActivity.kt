package com.ida.locde

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.ida.locde.ui.theme.DatabaseExample
import com.ida.locde.ui.theme.LocDETheme

class MainActivity : ComponentActivity() {
    private lateinit var db: DatabaseExample
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = Room.databaseBuilder(
            applicationContext,
            DatabaseExample::class.java,
            "users_db"
        )
            .allowMainThreadQueries()
            .build()

//        db.getDao().add(EntityExample(name = "Umida Reimbaeva",
//            phoneNumber = "+998913756365"))
//        db.getDao().add(EntityExample(name = "Anna Kim",
//            phoneNumber = "+998913756366"))
        setContent {
          MainUI()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainUI() {
        var showDialog by remember {
            mutableStateOf(false)
        }
        var users by remember {
            mutableStateOf(listOf<EntityExample>())
        }
        users = db.getDao().getAllDatas()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    showDialog = true
                }) {
                    Text("Add")
                }
            },
            topBar = {
                TopAppBar(
                    title = {
                        Text("Local db example")
                    }
                )
            }

        ) { iP ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(iP)
            ) {
                items(users) { user ->
                    Items(user,
                        {users = db.getDao().getAllDatas()})
                }
            }

        }

        if(showDialog) {
            AddingDialog({showDialog = false})
        }
    }

    @Composable
    fun Items(user: EntityExample,
              onUpdate:() -> Unit = {}) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement =
                Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row() {
                user.apply {
                    Text("$id", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(name, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(phoneNumber, fontSize = 18.sp)
                }
            }
            Image(
                painterResource(R.drawable.ic_delete),
                null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .clickable {
                        db.getDao().delete(user)
                        onUpdate()
                    })


        }
    }


    @Composable
    fun AddingDialog(closeDialog: () -> Unit) {
        //korsetiw korsetpew
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = {
                //ekran qalegen jerin, programma istemey
                closeDialog()
            },
            confirmButton = {
                Button(onClick = {
                    db.getDao().add(
                        EntityExample(
                            name = name, phoneNumber = phone
                        )
                    )
                    //delete
                    closeDialog()
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Text("Cancel", modifier = Modifier.clickable {
                    closeDialog()
                })
            },
            text = {
                Column() {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text("Name")
                        }
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                        },
                        label = {
                            Text("Phone")
                        }
                    )
                }
            }
        )


    }

}



/*Room kitapxanasi mn
islegende 3 tiykargi narse bar:
        Entity Dao Database*/
