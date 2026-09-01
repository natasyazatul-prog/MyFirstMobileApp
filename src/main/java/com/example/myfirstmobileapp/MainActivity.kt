package com.example.myfirstmobileapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size


//first page , main acticity
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme { //basic design to app
                ToDoApp() //nama app
            }
        }
    }
}

//screen , home tasks about

enum class Screen {
    HOME ,
    TASKS,
    ABOUT
}

//main app, control screen
@Composable
fun ToDoApp() {

    //first screen open apps
    var currentScreen by remember {
        mutableStateOf(Screen.HOME)
    }
    //list simpan task
    val taskList = remember {
        mutableStateListOf<String>()
    }

    when (currentScreen) {

        Screen.HOME -> {
            HomeScreen(

                //bila tekan viewtask
                onViewTasks = {
                    currentScreen = Screen.TASKS
                },

                //tekan aboutme
                onAbout = {
                    currentScreen = Screen.ABOUT
                }
            )
        }

        Screen.TASKS -> {
            TaskScreen(

                //send taskList to Task Screen
                taskList = taskList,

                //back button
                onBack = {
                    currentScreen = Screen.HOME
                }
            )
        }

        Screen.ABOUT -> {

            AboutScreen(

                onBack = {
                    currentScreen = Screen.HOME
                }
            )
        }
    }
}

//HOME SCREEN

@Composable
fun HomeScreen(
    onViewTasks: () -> Unit,
    onAbout: () -> Unit
) {

    Column(

        //use all screen
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        //content centre vertical
        verticalArrangement = Arrangement.Center,

        //content centre horizontal
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //gambar
        Image(
            painter = painterResource(id = R.drawable.todo),
            contentDescription = "To-Do List Icon",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Spacer(modifier = Modifier.height(20.dp))

        // app title

        Text(
            text = "My To-Do List",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        //description
        Text(
            text = "Manage your daily tasks easily."
        )

        Spacer(modifier = Modifier.height(30.dp))

        // button 1 - view tasks

        Button(
            onClick = {
                // go to task screen
                onViewTasks()
            },
            modifier = Modifier.width(220.dp)
        ) {
            Text("VIEW TASKS")
        }

        Spacer(modifier = Modifier.height(12.dp))

        //BUTTON 2 - ABOUT ME

        OutlinedButton(
            onClick = {
                //go to about screen
                onAbout()
            },
            modifier = Modifier.width(220.dp)
        ) {
            Text("ABOUT ME")
        }
    }
}
// SCREEN 2 - TASK SCREEN
// 1. Type task
// 2. Add task
// 3. View task
// 4. Mark task as Done
@Composable
fun TaskScreen(
    taskList: SnapshotStateList<String>,
    onBack: () -> Unit
) {

    // Variable untuk simpan text yang user taip
    var newTask by remember {
        mutableStateOf("")
    }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment . CenterHorizontally
        ) {
            //screen title
            Text(
                text = "My Tasks",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            // USER INPUT
            // User taip task dekat sini

            OutlinedTextField(

                // Text yang sedang ditaip
                value = newTask,

                // Setiap kali user taip, update variable newTask
                onValueChange = {
                    newTask = it
                },

                label = {
                    Text("Enter new task")
                },

                placeholder = {
                    Text("Example: Complete assignment")
                },

                modifier = Modifier.width(300.dp)
            )


            Spacer(modifier = Modifier.height(12.dp))

            //add task button
            Button(
                onClick = {

                    // Pastikan user tidak masukkan empty task
                    if (newTask.isNotBlank()) {

                        // Masukkan task dalam taskList
                        taskList.add(newTask.trim())

                        // Kosongkan TextField selepas task ditambah
                        newTask = ""
                    }

                },
                modifier = Modifier.width(300.dp)
            ) {

                Text("ADD TASK")
            }


            Spacer(modifier = Modifier.height(20.dp))

            // CHECK JIKA TASK LIST KOSONG
            if (taskList.isEmpty()) {

                Text(
                    text = "No tasks yet. Add your first task!",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign . Center
                )

            } else {

                Text(
                    text = "Task List",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )


                Spacer(modifier = Modifier.height(10.dp))
            }
            // PAPARKAN SEMUA TASK
            // LazyColumn lebih kurang macam list

            LazyColumn(
                modifier = Modifier.height(180.dp)
            ) {

                itemsIndexed(taskList) { index, task ->


                    // Setiap task akan ada dalam satu Row
                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),

                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        // Nombor + nama task
                        Text(
                            text = "${index + 1}. $task",

                            // weight buat text ambil ruang yang tinggal
                            modifier = Modifier.weight(1f)
                        )
                        // DONE BUTTON
                        // Bila tekan, task akan dibuang dari list

                        Button(
                            onClick = {

                                taskList.removeAt(index)

                            }
                        ) {

                            Text("DONE")
                        }
                    }


                    HorizontalDivider()
                }
            }


            Spacer(modifier = Modifier.height(10.dp))

            // BACK TO HOME BUTTON

            OutlinedButton(
                onClick = {

                    onBack()

                },
                modifier = Modifier.width(300.dp)
            ) {

                Text("BACK TO HOME")
            }
        }
    }


// SCREEN 3 - ABOUT SCREEN
// Tukar maklumat di bawah kepada maklumat awak

@Composable
fun AboutScreen(
    onBack: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //title
        Text(
            text = "About Me",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )


        Spacer(modifier = Modifier.height(30.dp))

        // STUDENT INFORMATION

        Text(
            text = "Student Name: HASNIZATUL NATASYA" ,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Student ID: AM2412018110",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Programme: DIPLOMA IN COMPUTER SCIENCE",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(10.dp))


        Text(
            text = "Course: Mobile Application Development",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(30.dp))

    //BACK BUTTON
        Button(
            onClick = {

                onBack()

            },
            modifier = Modifier.width(300.dp)
        ) {

            Text("BACK TO HOME")
        }
    }
}