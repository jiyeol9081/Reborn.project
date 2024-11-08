package org.advdropthebeatproject.reborn

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.Font
import reborn.composeapp.generated.resources.Res
import reborn.composeapp.generated.resources.Roboto_Bold
import reborn.composeapp.generated.resources.Roboto_Light
import reborn.composeapp.generated.resources.image1

enum class Screen {
    Start, Login, Home, Mypage, Upload, Select, Loading, Result, Question
}

val RobotoFontFamily
    @Composable get() = FontFamily(
    Font(Res.font.Roboto_Bold, weight = FontWeight.Bold),
    Font(Res.font.Roboto_Light, weight = FontWeight.Light)
)

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Start) }
    when (currentScreen) {
        Screen.Start -> StartScreen(
            onStartClick = { currentScreen = Screen.Login }
        )
        Screen.Login -> LoginScreen(
            onLoginClick = { currentScreen = Screen.Home },
            onBackClick = { currentScreen = Screen.Start }
        )
        Screen.Home -> HomeScreen(
            onUploadClick = { currentScreen = Screen.Upload },
            onMyPageClick = { currentScreen = Screen.Mypage }
        )
        Screen.Mypage -> MypageScreen(
            onHomeClick = { currentScreen = Screen.Home }
        )
        Screen.Upload -> UploadScreen(
            onContinueClick = { currentScreen = Screen.Select }
        )
        Screen.Select -> SelectScreen(
            onContinueClick = { currentScreen = Screen.Question }
        )
        Screen.Loading -> LoadingScreen(
            onLoadingFinished = { currentScreen = Screen.Result }
        )
        Screen.Result -> ResultScreen(
            onSaveClick = { currentScreen = Screen.Home },
            onAgainClick = { currentScreen = Screen.Select }
        )
        Screen.Question -> QuestionScreen(
            onContinueClick = { currentScreen = Screen.Loading },
            onSkipClick = { currentScreen = Screen.Loading }
        )
    }
}

@Composable
fun StartScreen(onStartClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Reborn",
                fontFamily = RobotoFontFamily,
                color = Color.Red,
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(bottom = 100.dp)
            )

            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "start",
                    fontFamily = RobotoFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginClick: () -> Unit, onBackClick: () -> Unit) {
    var id by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Login",
                fontFamily = RobotoFontFamily,
                color = Color.Red,
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                placeholder = { Text("ID") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = "Log in",
                    fontFamily = RobotoFontFamily ,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            }

            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = "back",
                    fontFamily = RobotoFontFamily,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
fun HomeScreen(onMyPageClick: () -> Unit, onUploadClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Reborn",
            fontFamily = RobotoFontFamily,
            color = Color.Red, // Red color
            fontSize = 40.sp,
            fontWeight = FontWeight.Light,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "안 입고 옷장에만 쌓이는옷들\n이제는 새롭게 태어날 시간\nReborn으로 옷에게 새 삶을",
            color = Color.Gray,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(280.dp))

        Row{
            Button(
                onClick = onUploadClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .weight(1f)
            ){
                Text(
                    text = "Reborn >",
                    fontFamily = RobotoFontFamily,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onMyPageClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .weight(1f)
            ){
                Text(
                    text = "MY page >",
                    fontFamily = RobotoFontFamily,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

        }
    }
}

@Composable
fun MypageScreen( onHomeClick:() -> Unit ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MY Reborn",
                fontFamily = RobotoFontFamily,
                color = Color.Red,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "CNU 님",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "최근",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 1..2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (j in 1..2) {

                        if (i==1 && j ==1){

                            Image(
                                painter = painterResource(resource = Res.drawable.image1),
                                contentDescription = "Reborn Image",
                                modifier = Modifier
                                    .size(150.dp)
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                contentScale = ContentScale.Crop
                            )
                        }else{
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(70.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = onHomeClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ){
                Text("Home")
            }
        }
    }
}

@Composable
fun UploadScreen(onContinueClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalPlatformContext.current

    var byteArray by remember { mutableStateOf(ByteArray(0)) }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
                    byteArray = it.readByteArray(context)
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "A new life with ",
            fontFamily = RobotoFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = Color.Gray
        )
        Text(
            text = "Reborn",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            color = Color.Red
        )

        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray.copy(alpha = 0.1f))
                .clickable {
                    pickerLauncher.launch()
                },
            contentAlignment = Alignment.Center
        ) {
            if (byteArray.isNotEmpty()) {
                AsyncImage(
                    model = byteArray,
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Upload",
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Button(
                onClick = { byteArray = ByteArray(0) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp)
            ) {
                Text("again")
            }

            Button(
                onClick = onContinueClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .width(120.dp)
                    .height(40.dp)
            ) {
                Text("continue")
            }
        }
    }
}

@Composable
fun SelectScreen(onContinueClick: () -> Unit) {
    var selectedItems by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "무엇으로 ",
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Reborn",
            fontFamily = RobotoFontFamily,
            color = Color.Red,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = "하시겠습니까?",
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        val items = listOf(
            "치마", "악세서리", "바지", "애견용", "반팔", "아이용",
            "긴팔", "원피스", "가방", "목도리", "민소매", "모자",
            "아우터", "장갑"
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.chunked(2).forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { item ->
                        val isSelected = item in selectedItems
                        Button(
                            onClick = {
                                selectedItems = if (isSelected) {
                                    selectedItems - item
                                } else {
                                    selectedItems + item
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (isSelected) Color.Black else Color.LightGray,
                                contentColor = if (isSelected) Color.White else Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                        ) {
                            Text(item, color = if (isSelected) Color.White else Color.Black)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onContinueClick,
            enabled = selectedItems.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (selectedItems.isNotEmpty()) Color.Black else Color.LightGray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("continue")
        }
    }
}

@Composable
fun QuestionScreen(onContinueClick: () -> Unit, onSkipClick: () -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "무엇으로 ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Text(
                text = "Reborn",
                fontFamily = RobotoFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                color = Color.Red
            )
            Text(
                text = " 하시겠습니까?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("원하는 스타일을 입력해주세요") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    textColor = Color.Black,
                    cursorColor = Color.Black
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.Black)
            )

            Text(
                text = "예) 캐주얼, 심플, 스포티 등",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 20.dp)
        ) {
            Button(
                onClick = onSkipClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("skip")
            }

            Button(
                onClick = onContinueClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text("continue")
            }
        }
    }
}

@Composable
fun LoadingScreen(onLoadingFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onLoadingFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Reborn - ing",
                fontFamily = RobotoFontFamily,
                color = Color.Red,
                fontSize = 32.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(color = Color.Gray)
        }
    }
}

@Composable
fun ResultScreen(onSaveClick: () -> Unit, onAgainClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "당신의 옷이",
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = "Reborn",
            color = Color.Red,
            fontFamily = RobotoFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light
        )

        Text(
            text = "되었습니다",
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Image(
            painter = painterResource(resource = Res.drawable.image1),
            contentDescription = "Reborn Image",
            modifier = Modifier
                .size(300.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Button(
                onClick = onAgainClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.width(100.dp)
            ) {
                Text("again")
            }

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.width(100.dp)
            ) {
                Text("save")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("저장하시겠습니까?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            onSaveClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("네")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("아니요")
                    }
                }
            )
        }
    }
}