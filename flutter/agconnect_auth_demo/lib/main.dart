/*
    Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:agconnect_auth/agconnect_auth.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: HomePage());
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  String _log = '';
  TextEditingController _countryCodeController = TextEditingController();
  TextEditingController _phoneNumberController = TextEditingController();
  TextEditingController _passwordController = TextEditingController();
  TextEditingController _verifyCodeController = TextEditingController();

  @override
  void initState() {
    super.initState();
    addTokenListener();
  }

  Future<void> addTokenListener() async {
    if (!mounted) return;

    AGCAuth.instance.tokenChanges().listen((TokenSnapshot event) {
      print("onEvent: $event , ${event.state}, ${event.token}");
    }, onError: (Object error) {
      print('onError: $error');
    });
  }

  _getCurrentUser() async {
    AGCAuth.instance.currentUser.then((value) {
      setState(() {
        _log = 'current user = ${value?.uid} , ${value?.providerId}';
      });
    });
  }

  _requestPhoneVerifyCode(VerifyCodeAction action) {
    String countryCode = _countryCodeController.text;
    String phoneNumber = _phoneNumberController.text;
    VerifyCodeSettings settings = VerifyCodeSettings(action, sendInterval: 30);
    PhoneAuthProvider.requestVerifyCode(countryCode, phoneNumber, settings).then((value) => print(value.validityPeriod));
  }

  _createPhoneUser() async {
    String countryCode = _countryCodeController.text;
    String phoneNumber = _phoneNumberController.text;
    String verifyCode = _verifyCodeController.text;
    String password = _passwordController.text;
    AGCAuth.instance.createPhoneUser(PhoneUser(countryCode, phoneNumber, verifyCode, password: password)) .then((value) {
      setState(() {
        _log = 'createPhoneUser = ${value.user.uid} , ${value.user.providerId}';
      });
    }).catchError((error)=>print(error));
  }

  _signInWithPassword() async {
    String countryCode = _countryCodeController.text;
    String phoneNumber = _phoneNumberController.text;
    String password = _passwordController.text;
    AGCAuthCredential credential = PhoneAuthProvider.credentialWithPassword(countryCode, phoneNumber, password);
    AGCAuth.instance.signIn(credential).then((value) {
      setState(() {
        _log = 'signInWithPassword = ${value.user.uid} , ${value.user.providerId}';
      });
    });
  }

  _signInWithVerifyCode() async {
    String countryCode = _countryCodeController.text;
    String phoneNumber = _phoneNumberController.text;
    String verifyCode = _verifyCodeController.text;
    String password = _passwordController.text;
    AGCAuthCredential credential = PhoneAuthProvider.credentialWithVerifyCode(countryCode, phoneNumber, verifyCode, password: password);
    AGCAuth.instance.signIn(credential).then((value) {
      setState(() {
        _log = 'signInWithVerifyCode = ${value.user.uid} , ${value.user.providerId}';
      });
    });
  }

  _signOut() {
    AGCAuth.instance.signOut().then((value) {
      setState(() {
        _log = 'signOut';
      });
    }).catchError((error)=>print(error));
  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Phone Auth'),
        ),
        body: Center(
            child: ListView(children: <Widget>[
              Text(_log),
              CupertinoTextField(controller: _countryCodeController,placeholder: 'country code',),
              CupertinoTextField(controller: _phoneNumberController,placeholder: 'phone number',),
              CupertinoTextField(controller: _passwordController,placeholder: 'password',),
              Row(
                mainAxisAlignment: MainAxisAlignment.start,
                children: <Widget>[
                  Expanded(
                    flex: 1,
                    child: CupertinoTextField(controller: _verifyCodeController,placeholder: 'verification code',),),
                  Container(width: 100,
                    height: 32,
                    child: FlatButton(color: Colors.white, child: Text('send'), onPressed: () => _requestPhoneVerifyCode(VerifyCodeAction.registerLogin)),),
                ],),
              CupertinoButton(child: Text('getCurrentUser'), onPressed: _getCurrentUser),
              CupertinoButton(child: Text('createPhoneUser'), onPressed: _createPhoneUser),
              CupertinoButton(child: Text('signInWithPassword'), onPressed: _signInWithPassword),
              CupertinoButton(child: Text('signInWithVerifyCode'), onPressed: _signInWithVerifyCode),
              CupertinoButton(child: Text('signOut'), onPressed: _signOut),
            ],)
        ),
      ),
    );
  }
}
