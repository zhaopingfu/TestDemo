import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CustomPaintRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Center(
          child: CustomPaint(
            size: Size(300, 300),
            painter: MyPainter(),
          ),
        ),
      ),
    );
  }
}

class MyPainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    double eWidth = size.width / 15.0;
    double eHeight = size.height / 15.0;

    // 棋盘背景
    var paint = Paint()
      ..isAntiAlias = true
      ..style = PaintingStyle.fill
      ..color = Color(0x77cdb175);
    canvas.drawRect(Offset.zero & size, paint);

    // 画棋盘网格
    paint
      ..style = PaintingStyle.stroke
      ..color = Colors.black87
      ..strokeWidth = 1.0;

    for (int i = 0; i <= 15; ++i) {
      double dy = eHeight * i;
      canvas.drawLine(Offset(0, dy), Offset(size.width, dy), paint);
    }

    for (int i = 0; i <= 15; ++i) {
      double dx = eWidth * i;
      canvas.drawLine(Offset(dx, 0), Offset(dx, size.height), paint);
    }

    // 画一个黑子
    paint
      ..style = PaintingStyle.fill
      ..color = Colors.black;
    canvas.drawCircle(Offset(size.width / 2 - eWidth / 2, size.height / 2 - eHeight / 2), min(eWidth / 2, eHeight / 2) - 2, paint);

    // 画一个白子
    paint..color = Colors.white;
    canvas.drawCircle(Offset(size.width / 2 + eWidth / 2, size.height / 2 - eHeight / 2), min(eWidth / 2, eHeight / 2) - 2, paint);
  }

  // 可以把棋盘，棋子分成两个控件，棋盘不需要重绘，提高性能
  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => false;
}
