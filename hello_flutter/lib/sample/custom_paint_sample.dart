import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CustomPaintSampleRoute extends StatelessWidget {
  const CustomPaintSampleRoute({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Stack(
          children: <Widget>[
            CustomPaint(
              size: const Size(300, 300),
              painter: CustomPaintBackgroundSample(),
            ),
            CustomPaint(
              size: const Size(300, 300),
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
    final double width = size.width / 15.0;
    final double height = size.height / 15.0;

    final Paint paint = Paint()
      ..isAntiAlias = true
      ..style = PaintingStyle.fill
      ..color = const Color(0x77cdb175);

    // 画背景
    canvas.drawRect(Offset.zero & size, paint);

    paint
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1.0
      ..color = Colors.black;

    // 画线
    for (int i = 0; i <= 15; i++) {
      final double dx = i * width;
      final double dy = i * height;
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
    final double width = size.width / 15.0;
    final double height = size.height / 15.0;

    final Paint paint = Paint()
      ..isAntiAlias = true
      ..style = PaintingStyle.fill
      ..color = Colors.black;

    final double radius = min(width / 2.0, height / 2.0) - 2.0;

    // 画一个黑子
    canvas.drawCircle(Offset(size.width / 2 - width / 2, size.height / 2 - height / 2), radius, paint);

    paint.color = Colors.white;

    // 画一个白子
    canvas.drawCircle(Offset(size.width / 2 + width / 2, size.height / 2 + height / 2), radius, paint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => false;
}
