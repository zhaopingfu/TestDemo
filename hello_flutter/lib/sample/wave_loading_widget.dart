import 'dart:math';
import 'dart:ui';

import 'package:flutter/material.dart' hide TextStyle;

class WaveLoadingWidget extends StatefulWidget {
  const WaveLoadingWidget(
      {Key? key,
      required this.text,
      required this.fontSize,
      required this.backgroundColor,
      required this.foregroundColor,
      required this.waveColor})
      : super(key: key);
  final String text;

  final double fontSize;

  final Color backgroundColor;

  final Color foregroundColor;

  final Color waveColor;

  @override
  _WaveLoadingWidgetState createState() => _WaveLoadingWidgetState();
}

class _WaveLoadingWidgetState extends State<WaveLoadingWidget> with SingleTickerProviderStateMixin {
  late AnimationController controller;

  late Animation<double> animation;

  @override
  void initState() {
    super.initState();
    controller = AnimationController(duration: const Duration(seconds: 1), vsync: this);
    controller.addStatusListener((AnimationStatus status) {
      switch (status) {
        case AnimationStatus.dismissed:
          print('--->> dismissed');
          break;
        case AnimationStatus.forward:
          print('--->> forward');
          break;
        case AnimationStatus.reverse:
          print('--->> reverse');
          break;
        case AnimationStatus.completed:
          print('--->> completed');
          break;
      }
    });

    animation = Tween<double>(begin: 0.0, end: 1.0).animate(controller)
      ..addListener(() {
        setState(() {});
      });
    controller.repeat();
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return CustomPaint(
      painter: WaveLoadingPainter(
        text: widget.text,
        fontSize: widget.fontSize,
        animatedValue: animation.value,
        backgroundColor: widget.backgroundColor,
        foregroundColor: widget.foregroundColor,
        waveColor: widget.waveColor,
      ),
    );
  }
}

/// 如果外部没有指定颜色值，则使用此默认颜色值
const Color defaultColor = Colors.lightBlue;

class WaveLoadingPainter extends CustomPainter {
  WaveLoadingPainter(
      {required this.text,
      required this.fontSize,
      required this.animatedValue,
      required this.backgroundColor,
      required this.foregroundColor,
      required this.waveColor}) {
    _paint
      ..isAntiAlias = true
      ..style = PaintingStyle.fill
      ..strokeWidth = 3
      ..color = waveColor;
  }

  /// 画笔对象
  final Paint _paint = Paint();

  /// 圆形路径
  final Path _circlePath = Path();

  /// 波浪路径
  final Path _wavePath = Path();

  /// 要显示的文本
  final String text;

  final double? fontSize;
  final double animatedValue;
  final Color? backgroundColor;
  final Color? foregroundColor;
  final Color waveColor;

  @override
  void paint(Canvas canvas, Size size) {
    final double side = min(size.width, size.height);
    final double radius = side / 2.0;

    _drawText(canvas: canvas, side: side, colors: backgroundColor);

    _circlePath.reset();
    _circlePath.addArc(Rect.fromLTWH(0, 0, side, side), 0, 2 * pi);

    final double waveWidth = side * 0.8;
    final double waveHeight = side / 6.0;
    _wavePath.reset();
    _wavePath.moveTo((animatedValue - 1) * waveWidth, radius);
    for (double i = -waveWidth; i < side; i += waveWidth) {
      _wavePath.relativeQuadraticBezierTo(waveWidth / 4.0, -waveHeight, waveWidth / 2.0, 0);
      _wavePath.relativeQuadraticBezierTo(waveWidth / 4.0, waveHeight, waveWidth / 2.0, 0);
    }
    _wavePath.relativeLineTo(0, radius);
    _wavePath.lineTo(-waveWidth, side);
    _wavePath.close();

    final Path combine = Path.combine(PathOperation.intersect, _circlePath, _wavePath);
    canvas.drawPath(combine, _paint);

    canvas.clipPath(combine);
    _drawText(canvas: canvas, side: side, colors: foregroundColor);
  }

  void _drawText({required Canvas canvas, required double side, Color? colors}) {
    final ParagraphBuilder pb = ParagraphBuilder(
      ParagraphStyle(
        textAlign: TextAlign.center,
        fontStyle: FontStyle.normal,
        fontSize: fontSize ?? 0,
      ),
    );
    pb.pushStyle(TextStyle(color: colors ?? defaultColor));
    pb.addText(text);
    final ParagraphConstraints pc = ParagraphConstraints(width: fontSize ?? 0);
    final Paragraph paragraph = pb.build()..layout(pc);
    canvas.drawParagraph(paragraph, Offset((side - paragraph.width) / 2.0, (side - paragraph.height) / 2.0));
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return true;
  }
}
