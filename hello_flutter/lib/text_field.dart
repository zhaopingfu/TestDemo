import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class TextFieldDemo extends StatelessWidget {
  const TextFieldDemo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: const Text('文本字段'),
      ),
      body: const TextFormFieldDemo(),
    );
  }
}

class TextFormFieldDemo extends StatefulWidget {
  const TextFormFieldDemo({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => TextFormFieldDemoState();
}

class TextFormFieldDemoState extends State<TextFormFieldDemo> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();

  PersonData person = PersonData();

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  AutovalidateMode _autoValidateMode = AutovalidateMode.disabled;

  String? _validateName(String? value) {
    if (value == null || value.isEmpty) {
      return '必须填写姓名。';
    }
    final RegExp nameExp = RegExp(r'^[A-Za-z ]+$');
    if (!nameExp.hasMatch(value)) {
      return '请只输入字母字符。';
    }
    return null;
  }

  String? _validateEmail(String? value) {
    if (value == null || value.isEmpty) {
      return '必须填写邮箱。';
    }
    return null;
  }

  void _handleSubmitted() {
    final FormState? form = _formKey.currentState;
    if (!(form?.validate() ?? false)) {
      _autoValidateMode = AutovalidateMode.always;
      print('请先修正红色错误，然后再提交。');
    } else {
      form?.save();
      print('${person.name}的邮箱是 ${person.email}');
    }
  }

  @override
  Widget build(BuildContext context) {
    const SizedBox sizedBoxSpace = SizedBox(height: 24);

    return Scaffold(
      key: _scaffoldKey,
      body: Form(
        key: _formKey,
        autovalidateMode: _autoValidateMode,
        child: Scrollbar(
          child: SingleChildScrollView(
            dragStartBehavior: DragStartBehavior.down,
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                sizedBoxSpace,
                TextFormField(
                  textCapitalization: TextCapitalization.words,
                  decoration: const InputDecoration(
                    filled: true,
                    icon: Icon(Icons.person),
                    hintText: '人们如何称呼您？',
                    labelText: '姓名*',
                  ),
                  onSaved: (String? value) {
                    person.name = value ?? '';
                  },
                  validator: _validateName,
                ),
                sizedBoxSpace,
                TextFormField(
                  decoration: const InputDecoration(
                    filled: true,
                    icon: Icon(Icons.email),
                    hintText: '您的电子邮件地址',
                    labelText: '电子邮件',
                  ),
                  keyboardType: TextInputType.emailAddress,
                  onSaved: (String? value) {
                    person.email = value ?? '';
                  },
                  validator: _validateEmail,
                ),
                sizedBoxSpace,
                Center(
                  child: ElevatedButton(
                    child: const Text('提交'),
                    onPressed: _handleSubmitted,
                  ),
                ),
                sizedBoxSpace,
                Text(
                  '* 表示必填字段',
                  style: Theme.of(context).textTheme.caption,
                ),
                sizedBoxSpace,
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class PersonData {
  String name = '';
  String phoneNumber = '';
  String email = '';
  String password = '';
}
