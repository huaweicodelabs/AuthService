using System;
using Android.App;
using Android.OS;
using Android.Runtime;
using Android.Views;
using AndroidX.AppCompat.Widget;
using AndroidX.AppCompat.App;
using Google.Android.Material.FloatingActionButton;
using Google.Android.Material.Snackbar;
using Android.Widget;
using Huawei.Agconnect.Auth;
using Java.Util;
using Android.Util;
using Android.Content;
using Android.Text;

namespace XamarinAuthCodelab
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme.NoActionBar", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {
        private const String TAG = "MainActivity";
        private EditText countryCode;
        private EditText phoneNumber;
        private EditText password;
        private EditText verifyCode;
        private Button send;
        private Button signIn;
        private Button signUp;
        private Button anonymous;
        private TextView resultConsole;


        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            SetContentView(Resource.Layout.activity_main);
            countryCode = FindViewById<EditText>(Resource.Id.edtCountryCode);
            phoneNumber = FindViewById<EditText>(Resource.Id.edtAccount);
            password = FindViewById<EditText>(Resource.Id.edtPassword);
            verifyCode = FindViewById<EditText>(Resource.Id.edtVerifyCode);
            send = FindViewById<Button>(Resource.Id.btnSend);
            signIn = FindViewById<Button>(Resource.Id.btnLogin);
            signUp = FindViewById<Button>(Resource.Id.btn_signup);
            anonymous = FindViewById<Button>(Resource.Id.btnLoginAnonymous);
            resultConsole = FindViewById<TextView>(Resource.Id.console);

            send.Click += Send_Click;
            signIn.Click += SignIn_Click;
            signUp.Click += SignUp_Click;
            anonymous.Click += Anonymous_Click;
        }

        private async void Anonymous_Click(object sender, EventArgs e)
        {
            try
            {
                var signInAnonymous = AGConnectAuth.Instance.SignInAnonymouslyAsync();
                ISignInResult result = await signInAnonymous;

                if (signInAnonymous.Status.Equals(System.Threading.Tasks.TaskStatus.RanToCompletion))
                {
                    Log.Debug(TAG, "Is Anonymous:" + result.User.IsAnonymous.ToString());
                    resultConsole.Text = result.User.Uid;
                }
            }
            catch (Exception ex)
            {
                Log.Error(TAG, ex.Message);
            }
        }

        private async void SignUp_Click(object sender, EventArgs e)
        {
            string code = countryCode.Text.ToString().Trim();
            string phone = phoneNumber.Text.ToString().Trim();
            string pass_word = password.Text.ToString().Trim();
            string verify = verifyCode.Text.ToString().Trim();

            // Build phone user.
            PhoneUser phoneUser = new PhoneUser.Builder()
                .SetCountryCode(code)
                .SetPhoneNumber(phone)
                .SetPassword(pass_word)
                .SetVerifyCode(verify)
                .Build();

            try
            {
                // Create phoneUser user.
                var phoneUserResult = AGConnectAuth.Instance.CreateUserAsync(phoneUser);
                ISignInResult result = await phoneUserResult;
                if (phoneUserResult.Status.Equals(System.Threading.Tasks.TaskStatus.RanToCompletion))
                {
                    resultConsole.Text = result.User.Uid;
                }
            }
            catch (Exception ex)
            {
                Toast.MakeText(this, "Create User Fail:" + ex.Message, ToastLength.Long).Show();
            }
        }

        private async void SignIn_Click(object sender, EventArgs e)
        {
            string code = countryCode.Text.ToString().Trim();
            string phone = phoneNumber.Text.ToString().Trim();
            string pass_word = password.Text.ToString().Trim();
            string verify = verifyCode.Text.ToString().Trim();
            IAGConnectAuthCredential credential;
            if (TextUtils.IsEmpty(verify))
            {
                credential = PhoneAuthProvider.CredentialWithPassword(code, phone, pass_word);
            }
            else
            {
                credential = PhoneAuthProvider.CredentialWithVerifyCode(code, phone, pass_word, verify);
            }
            try
            {
                AGConnectAuth connectAuth = AGConnectAuth.Instance;
                var signInResult = AGConnectAuth.Instance.SignInAsync(credential);

                ISignInResult result = await signInResult;

                if (signInResult.Status.Equals(System.Threading.Tasks.TaskStatus.RanToCompletion))
                {
                    Log.Debug(TAG, signInResult.Result.ToString());
                    resultConsole.Text = result.User.Uid;
                }
            }
            catch (Exception ex)
            {
                Log.Error(TAG, ex.Message);
                Toast.MakeText(this, "SignIn failed: " + ex.Message, ToastLength.Long).Show();
            }
        }

        private void Send_Click(object sender, EventArgs e)
        {
            VerifyCodeSettings settings = VerifyCodeSettings.NewBuilder()
                .Action(VerifyCodeSettings.ActionRegisterLogin)
                .SendInterval(30)
                .Locale(Locale.English)
                .Build();
            string code = countryCode.Text.ToString().Trim();
            string number = phoneNumber.Text.ToString().Trim();

            try
            {
                PhoneAuthProvider.RequestVerifyCode(code, number, settings);
                Log.Info(TAG, "RequestVerifyCode function called successfully.");
                Toast.MakeText(this, "verifyCode send successfully", ToastLength.Long).Show();
            }
            catch (Exception ex)
            {
                Toast.MakeText(this, ex.Message, ToastLength.Long).Show();
            }
        }
    }
}
