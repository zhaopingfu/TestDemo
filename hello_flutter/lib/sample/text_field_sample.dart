import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hello_flutter/text_field.dart';

class TextFieldSampleRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Text('文本字段'),
      ),
      body: TextFormFieldSample(),
    );
  }
}

class TextFormFieldSample extends StatefulWidget {
  const TextFormFieldSample({Key key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _TextFormFieldSample();
}

class _TextFormFieldSample extends State<TextFormFieldSample> {
  GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  AutovalidateMode _autovalidateMode = AutovalidateMode.always;
  PersonData _person = PersonData();

  String _validateName(String value) {
    if (value == null || value.isEmpty) {
      return '请输入姓名';
    }
    return null;
  }

  String _validateEmail(String value) {
    if (value == null || value.isEmpty) {
      return '请输入邮箱';
    }
    return null;
  }

  void _handleSubmitted() {
    final form = _formKey.currentState;
    if (form.validate()) {
      form.save();
      print('${_person.name}的邮箱是 ${_person.email}');
    } else {
      _autovalidateMode = AutovalidateMode.always;
      print('请先修改红色错误');
    }
  }

  @override
  Widget build(BuildContext context) {
    const sizedBoxSpace = SizedBox(height: 24);
    return Scaffold(
      key: _scaffoldKey,
      body: Form(
        key: _formKey,
        autovalidateMode: _autovalidateMode,
        child: Scrollbar(
          child: SingleChildScrollView(
            child: Column(
              children: [
                sizedBoxSpace,
                TextFormField(
                  textCapitalization: TextCapitalization.words,
                  decoration: InputDecoration(filled: true, icon: Icon(Icons.person), hintText: '人们如何称呼您', labelText: '姓名'),
                  maxLength: 10,
                  keyboardType: TextInputType.number,
                  inputFormatters: [
                    FilteringTextInputFormatter.allow(RegExp("[0-9]")), //限制只允许输入字母和数字
//                    WhitelistingTextInputFormatter.digitsOnly,                //限制只允许输入数字
//                    LengthLimitingTextInputFormatter(8),                      //限制输入长度不超过8位
                  ],
                  onSaved: (value) {
                    _person.name = value;
                  },
                  validator: _validateName,
                ),
                sizedBoxSpace,
                TextFormField(
                  decoration: InputDecoration(filled: true, icon: Icon(Icons.email), hintText: '您的电子邮箱', labelText: '电子邮箱'),
                  keyboardType: TextInputType.emailAddress,
                  onSaved: (value) {
                    _person.email = value;
                  },
                  validator: _validateEmail,
                ),
                sizedBoxSpace,
                Center(
                  child: RaisedButton(
                    child: Text('提交'),
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
