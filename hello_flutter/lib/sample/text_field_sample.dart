import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hello_flutter/text_field.dart';

class TextFieldSampleRoute extends StatelessWidget {
  const TextFieldSampleRoute({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: const Text('文本字段'),
      ),
      body: const TextFormFieldSample(),
    );
  }
}

class TextFormFieldSample extends StatefulWidget {
  const TextFormFieldSample({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _TextFormFieldSample();
}

class _TextFormFieldSample extends State<TextFormFieldSample> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  AutovalidateMode _autovalidateMode = AutovalidateMode.always;
  final PersonData _person = PersonData();

  String? _validateName(String? value) {
    if (value == null || value.isEmpty) {
      return '请输入姓名';
    }
    return null;
  }

  String? _validateEmail(String? value) {
    if (value == null || value.isEmpty) {
      return '请输入邮箱';
    }
    return null;
  }

  void _handleSubmitted() {
    final FormState? form = _formKey.currentState;
    if (form?.validate() ?? false) {
      form?.save();
      print('${_person.name}的邮箱是 ${_person.email}');
    } else {
      _autovalidateMode = AutovalidateMode.always;
      print('请先修改红色错误');
    }
  }

  @override
  Widget build(BuildContext context) {
    const SizedBox sizedBoxSpace = SizedBox(height: 24);
    return Scaffold(
      key: _scaffoldKey,
      body: Form(
        key: _formKey,
        autovalidateMode: _autovalidateMode,
        child: Scrollbar(
          child: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                sizedBoxSpace,
                TextFormField(
                  textCapitalization: TextCapitalization.words,
                  decoration: const InputDecoration(
                      filled: true, icon: Icon(Icons.person), hintText: '人们如何称呼您', labelText: '姓名'),
                  maxLength: 10,
                  keyboardType: TextInputType.number,
                  inputFormatters: <TextInputFormatter>[
                    FilteringTextInputFormatter.allow(RegExp('[0-9]')), //限制只允许输入字母和数字
//                    WhitelistingTextInputFormatter.digitsOnly,                //限制只允许输入数字
//                    LengthLimitingTextInputFormatter(8),                      //限制输入长度不超过8位
                  ],
                  onSaved: (String? value) {
                    _person.name = value ?? '';
                  },
                  validator: _validateName,
                ),
                sizedBoxSpace,
                TextFormField(
                  decoration: const InputDecoration(
                      filled: true, icon: Icon(Icons.email), hintText: '您的电子邮箱', labelText: '电子邮箱'),
                  keyboardType: TextInputType.emailAddress,
                  onSaved: (String? value) {
                    _person.email = value ?? '';
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
              ],
            ),
          ),
        ),
      ),
    );
  }
}
