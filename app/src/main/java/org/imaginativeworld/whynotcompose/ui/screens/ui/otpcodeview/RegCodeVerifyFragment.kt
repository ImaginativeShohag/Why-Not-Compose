package org.imaginativeworld.whynotcompose.ui.screens.ui.otpcodeview

//@AndroidEntryPoint
//class RegCodeVerifyFragment : Fragment(), CommonFunctions {
//
//    private val args: RegCodeVerifyFragmentArgs by navArgs()
//
//    private var listener: OnFragmentInteractionListener? = null
//
//    @Inject
//    lateinit var viewModel: RegCodeVerifyViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        initObservers()
//    }
//
//    @ExperimentalAnimationApi
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                AppTheme {
//                    ProvideWindowInsets {
//                        MainContainer(
//                            phoneNumber = args.phoneNumber,
//                            eventShowMessage = viewModel.eventShowMessage,
//                            resetEventShowMessage = viewModel::resetEventShowMessage,
//                            verify = ::verify,
//                            isValidInputs = viewModel::isValidInputs,
//                            counterValue = viewModel.resetCounterValue,
//                            resendCode = {
//                                when (args.target) {
//                                    Constants.VALUE_TARGET_REGISTER, Constants.VALUE_TARGET_SOCIAL_REGISTER -> viewModel.sendRegisterOtp(
//                                        args.phoneNumber
//                                    )
//                                    Constants.VALUE_TARGET_FORGET_PASSWORD -> viewModel.sendForgetPassOtp(
//                                        args.phoneNumber
//                                    )
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    private fun verify(
//        phoneNumber: String,
//        code: String
//    ) {
//        when (args.target) {
//            Constants.VALUE_TARGET_REGISTER, Constants.VALUE_TARGET_SOCIAL_REGISTER -> viewModel.verify(
//                phoneNumber,
//                code
//            )
//            Constants.VALUE_TARGET_FORGET_PASSWORD -> viewModel.forgetVerify(phoneNumber, code)
//        }
//    }
//
//    private fun gotoPasswordReset(token: String) {
//        val action = AuthNavGraphDirections.actionGlobalForgetPasswordFragment(token)
//        listener?.gotoFragment(action)
//    }
//
//    private fun gotoSignUp(phoneNumber: String, token: String) {
//        val action = AuthNavGraphDirections.actionGlobalSignUpFragment(
//            phoneNumber,
//            token,
//            args.userName,
//            args.userEmail,
//            args.providerId,
//            args.providerName
//        )
//        listener?.gotoFragment(action)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        listener?.hideLoading()
//
//        viewModel.restartCounter()
//    }
//
//    override fun initObservers() {
//        viewModel.eventShowLoading.observe(this, {
//            it?.let { showLoading ->
//                if (showLoading) {
//                    listener?.showLoading()
//                } else {
//                    listener?.hideLoading()
//                }
//            }
//        })
//
//        viewModel.verifyResponse.observe(this, {
//            it?.let { response ->
//                when (args.target) {
//                    Constants.VALUE_TARGET_REGISTER, Constants.VALUE_TARGET_SOCIAL_REGISTER -> gotoSignUp(
//                        args.phoneNumber,
//                        response.data?.token ?: ""
//                    )
//                    Constants.VALUE_TARGET_FORGET_PASSWORD -> gotoPasswordReset(
//                        response.data?.token ?: ""
//                    )
//                }
//            }
//        })
//
//        viewModel.sendOtpSuccess.observe(this, {
//            it?.let {
//                Toast.makeText(
//                    requireContext(),
//                    "OTP code re-sent successfully.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        Timber.d("onAttach")
//
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException("$context must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Timber.d("onDetach")
//
//        listener = null
//    }
//}
//
//@ExperimentalAnimationApi
//@Preview
//@Composable
//private fun RegCodeVerifyContainerPreview() {
//    val eventShowMessage = remember { mutableStateOf<String?>(null) }
//
//    AppTheme {
//        MainContainer(
//            phoneNumber = "+8801813173443",
//            eventShowMessage = eventShowMessage,
//            resetEventShowMessage = {},
//            verify = { _, _ -> },
//            isValidInputs = { false },
//            counterValue = MutableLiveData(),
//            resendCode = {}
//        )
//    }
//}
//
//@ExperimentalAnimationApi
//@Composable
//private fun MainContainer(
//    phoneNumber: String,
//    eventShowMessage: State<String?>,
//    resetEventShowMessage: () -> Unit,
//    verify: (phoneNumber: String, code: String) -> Unit,
//    isValidInputs: (code: String) -> Boolean,
//    counterValue: LiveData<String>,
//    resendCode: () -> Unit
//) {
//    val systemUiController = rememberSystemUiController()
//
//    val scaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()
//
//    val codeState = remember { mutableStateOf("") }
//
//    val customKeyboardShow = remember { mutableStateOf(false) }
//
//    val counterValueState = counterValue.observeAsState("00:00")
//
//    SideEffect {
//        systemUiController.setSystemBarsColor(
//            color = Color.Transparent,
//            darkIcons = true
//        )
//    }
//
//    Scaffold(
//        scaffoldState = scaffoldState,
//        snackbarHost = { CustomSnackbarHost(it) },
//        modifier = Modifier
//            .background(MaterialTheme.colors.surface)
//            .navigationBarsPadding()
//    ) {
//        eventShowMessage.value?.let { message ->
//            resetEventShowMessage()
//
//            scope.launch {
//                scaffoldState.snackbarHostState.showSnackbar(message)
//            }
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//        ) {
//
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .verticalScroll(rememberScrollState()),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Top
//            ) {
//
//                Image(
//                    painter = painterResource(id = R.drawable.otp_illustration),
//                    contentDescription = "illustration",
//                    modifier = Modifier
//                        .padding(top = 64.dp)
//                        .size(128.dp)
//                )
//
//                Title(
//                    text = "Verify Mobile Number",
//                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
//                )
//
//                Text(
//                    text = "Enter the one time password send to",
//                    modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 0.dp),
//                    textAlign = TextAlign.Center,
//                    fontSize = 15.sp,
//                    lineHeight = 23.sp,
//                    color = Color(0xFF677987)
//                )
//
//                Text(
//                    text = phoneNumber,
//                    modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
//                    textAlign = TextAlign.Center,
//                    fontSize = 16.sp,
//                    color = Color(0xFF677987)
//                )
//
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(32.dp, 16.dp, 32.dp, 16.dp)
//                        .clickable(
//                            onClick = {
//                                customKeyboardShow.value = !customKeyboardShow.value
//                            },
//                            indication = null,
//                            interactionSource = remember { MutableInteractionSource() }
//                        ),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    CodeField(
//                        text = if (codeState.value.length > 0) codeState.value.substring(
//                            TextRange(
//                                0,
//                                1
//                            )
//                        ) else ""
//                    )
//
//                    CodeField(
//                        text = if (codeState.value.length > 1) codeState.value.substring(
//                            TextRange(
//                                1,
//                                2
//                            )
//                        ) else ""
//                    )
//
//                    CodeField(
//                        text = if (codeState.value.length > 2) codeState.value.substring(
//                            TextRange(
//                                2,
//                                3
//                            )
//                        ) else ""
//                    )
//
//                    CodeField(
//                        text = if (codeState.value.length > 3) codeState.value.substring(
//                            TextRange(
//                                3,
//                                4
//                            )
//                        ) else ""
//                    )
//
//                    CodeField(
//                        text = if (codeState.value.length > 4) codeState.value.substring(
//                            TextRange(
//                                4,
//                                5
//                            )
//                        ) else ""
//                    )
//
//                    CodeField(
//                        text = if (codeState.value.length > 5) codeState.value.substring(
//                            TextRange(
//                                5,
//                                6
//                            )
//                        ) else ""
//                    )
//                }
//
//                Text(
//                    text = "Having trouble? Request a new OTP in ${counterValueState.value}",
//                    modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
//                    textAlign = TextAlign.Center,
//                    fontSize = 14.sp,
//                    lineHeight = 16.sp,
//                    color = Color(0xFF677987)
//                )
//
//                DefaultTextButton(
//                    modifier = Modifier.padding(bottom = 16.dp),
//                    text = "Resend Code",
//                    enabled = counterValueState.value == "00:00"
//                ) {
//                    resendCode()
//                }
//
//            }
//
//            AnimatedVisibility(
//                visible = customKeyboardShow.value,
//                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn(),
//                exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut(),
//            ) {
//                VirtualNumberKeyboard(
//                    codeState = codeState,
//                    isValidInputs = isValidInputs,
//                    verify = { code ->
//                        verify(phoneNumber, code)
//                    }
//                )
//            }
//        }
//
//    }
//}
//
//@Preview
//@Composable
//fun VirtualNumberKeyboardPreview() {
//    VirtualNumberKeyboard(
//        codeState = remember { mutableStateOf("") },
//        isValidInputs = { true },
//        verify = {}
//    )
//}
//
//@Composable
//fun VirtualNumberKeyboard(
//    modifier: Modifier = Modifier,
//    codeState: MutableState<String>,
//    isValidInputs: (code: String) -> Boolean,
//    verify: (code: String) -> Unit
//) {
//    Column(
//        modifier
//            .fillMaxWidth()
//            .background(Color(0xFFF2F2F2))
//    ) {
//        Row(
//            Modifier.fillMaxWidth()
//        ) {
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "1",
//                onClick = { if (codeState.value.length < 6) codeState.value += "1" }
//            )
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "2",
//                onClick = { if (codeState.value.length < 6) codeState.value += "2" }
//            )
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "3",
//                onClick = { if (codeState.value.length < 6) codeState.value += "3" }
//            )
//        }
//        Row(
//            Modifier.fillMaxWidth()
//        ) {
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "4",
//                onClick = { if (codeState.value.length < 6) codeState.value += "4" }
//            )
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "5",
//                onClick = { if (codeState.value.length < 6) codeState.value += "5" }
//            )
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "6",
//                onClick = { if (codeState.value.length < 6) codeState.value += "6" }
//            )
//        }
//        Row(
//            Modifier.fillMaxWidth()
//        ) {
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "7",
//                onClick = { if (codeState.value.length < 6) codeState.value += "7" }
//            )
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "8",
//                onClick = { if (codeState.value.length < 6) codeState.value += "8" }
//            )
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "9",
//                onClick = { if (codeState.value.length < 6) codeState.value += "9" }
//            )
//        }
//        Row(
//            Modifier.fillMaxWidth()
//        ) {
//            Column(
//                modifier = Modifier
//                    .height(54.dp)
//                    .weight(1f)
//                    .clickable(
//                        onClick = {
//                            if (codeState.value.isNotEmpty()) {
//                                codeState.value = codeState.value.substring(
//                                    TextRange(
//                                        0,
//                                        codeState.value.length - 1
//                                    )
//                                )
//                            }
//                        },
//                        indication = rememberRipple(color = MaterialTheme.colors.primary),
//                        interactionSource = remember { MutableInteractionSource() }
//                    ),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Image(
//                    modifier = Modifier.size(38.dp),
//                    painter = painterResource(id = R.drawable.ic_keyboard_delete),
//                    contentDescription = "Delete"
//                )
//            }
//            KeyboardKey(
//                modifier = Modifier.weight(1f),
//                text = "0",
//                onClick = { if (codeState.value.length < 6) codeState.value += "0" }
//            )
//            Column(
//                modifier = Modifier
//                    .height(54.dp)
//                    .weight(1f)
//                    .clickable(
//                        onClick = {
//                            if (codeState.value.length >= 6 && isValidInputs(codeState.value)) {
//                                verify(codeState.value)
//                            }
//                        },
//                        indication = rememberRipple(color = MaterialTheme.colors.primary),
//                        interactionSource = remember { MutableInteractionSource() }
//                    ),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Crossfade(codeState.value.length >= 6) { isCompleted ->
//                    when (isCompleted) {
//                        true -> Image(
//                            modifier = Modifier.size(38.dp),
//                            painter = painterResource(id = R.drawable.ic_keyboard_enter_enabled),
//                            contentDescription = "Enter"
//                        )
//                        false -> Image(
//                            modifier = Modifier.size(38.dp),
//                            painter = painterResource(id = R.drawable.ic_keyboard_enter),
//                            contentDescription = "Enter"
//                        )
//                    }
//                }
//
//            }
//        }
//    }
//}
//
//@Composable
//private fun KeyboardKey(
//    modifier: Modifier,
//    text: String,
//    onClick: () -> Unit
//) {
//    TextButton(
//        modifier = modifier.height(54.dp),
//        onClick = onClick,
//        shape = RoundedCornerShape(0.dp),
//        contentPadding = PaddingValues(0.dp)
//    ) {
//        Text(
//            text = text,
//            fontSize = 32.sp,
//            fontFamily = Fonts.Latinka,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF253E4E)
//        )
//    }
//}
//
//@Composable
//private fun CodeField(
//    text: String
//) {
//    Box(
//        Modifier
//            .padding(start = 4.dp, end = 4.dp)
//            .size(40.dp, 45.dp)
//            .background(
//                color = Color(0xFFF1F3F2),
//                shape = RoundedCornerShape(6.dp)
//            )
//            .border(
//                BorderStroke(1.dp, Color(0xFFDDDDDD)),
//                RoundedCornerShape(6.dp)
//            )
//    ) {
//        Text(
//            modifier = Modifier.align(Alignment.Center),
//            text = text,
//            fontSize = 20.sp,
//            fontFamily = Fonts.Latinka,
//            fontWeight = FontWeight.Medium,
//            color = Color(0xFF2F4858)
//        )
//        if (text.isEmpty()) {
//            Box(
//                Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(start = 12.dp, end = 12.dp, bottom = 13.dp)
//                    .height(1.dp)
//                    .fillMaxWidth()
//                    .background(Color(0xFFC5C9C7))
//            )
//        }
//    }
//}