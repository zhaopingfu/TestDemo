import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CustomPaintSampleRoute extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Stack(
          children: [
            CustomPaint(
              size: Size(300, 300),
              painter: CustomPaintBackgroundSample(),
            ),
            CustomPaint(
              size: Size(300, 300),
              painter: CustomPaintForegroundSample(),
            ),
          ],
        ),
      ),
    );
  }
}

class CustomPaintBackgroundSample extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    var width = size.width / 15.0;
    var height = size.height / 15.0;

    var paint = Paint()
      ..isAntiAlias = true
      ..style = PaintingStyle.fill
      ..color = Color(0x77cdb175);

    // 画背景
    canvas.drawRect(Offset.zero & size, paint);

    paint
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.0
      ..color = Colors.black;

    // 画线
    for (int i = 0; i <= 15; i++) {
      var dx = i * width;
      var dy = i * height;
      canvas.drawLine(Offset(dx, 0), Offset(dx, size.height), paint);
      canvas.drawLine(Offset(0, dy), Offset(size.width, dy), paint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => false;
}

class CustomPaintForegroundSample extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    var width = size.width / 15.0;
    var height = size.height / 15.0;

    var paint = Paint()
      ..isAntiAlias = true
      ..style = PaintingStyle.fill
      ..color = Colors.black;

    double radius = min(width / 2.0, height / 2.0) - 2.0;

    // 画一个黑子
    canvas.drawCircle(Offset(size.width / 2 - width / 2, size.height / 2 - height / 2), radius, paint);

    paint.color = Colors.white;

    // 画一个白子
    canvas.drawCircle(Offset(size.width / 2 + width / 2, size.height / 2 + height / 2), radius, paint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => false;
}
