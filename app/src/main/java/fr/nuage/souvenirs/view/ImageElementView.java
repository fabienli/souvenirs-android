package fr.nuage.souvenirs.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;

import fr.nuage.souvenirs.R;
import fr.nuage.souvenirs.view.helpers.ElementMoveDragListener;
import fr.nuage.souvenirs.viewmodel.ImageElementViewModel;
import fr.nuage.souvenirs.viewmodel.PageViewModel;


public class ImageElementView extends AppCompatImageView {

    private Paint contourPaint;
    private Rect rect = new Rect();

    public ImageElementView(Context context, PageViewModel pageViewModel, ImageElementViewModel imageElementViewModel) {
        super(context);

        contourPaint = new Paint();
        contourPaint.setAntiAlias(true);
        contourPaint.setColor(getResources().getColor(R.color.primaryDarkColor));
        contourPaint.setStrokeWidth(getResources().getDimension(R.dimen.selected_strokewidth));
        contourPaint.setStyle(Paint.Style.STROKE);

        ElementMoveDragListener elementMoveDragListener = new ElementMoveDragListener(pageViewModel, imageElementViewModel, (AppCompatActivity)context);
        pageViewModel.getLdPaintMode().observe((AppCompatActivity)context, paintMode -> {
            if (paintMode) {
                setOnClickListener(null);
                setOnTouchListener(null);
                setOnLongClickListener(null);
                setOnDragListener(null);
            } else {
                setOnClickListener(elementMoveDragListener);
                setOnTouchListener(elementMoveDragListener);
                setOnLongClickListener(elementMoveDragListener);
                setOnDragListener(elementMoveDragListener);
            }
        });

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected()) {
            getDrawingRect(rect);
            //draw circles for image resize UI
            canvas.drawCircle(rect.right,rect.bottom,getResources().getDimension(R.dimen.selected_circle_ctl),contourPaint);
            canvas.drawCircle(rect.left,rect.top,getResources().getDimension(R.dimen.selected_circle_ctl),contourPaint);
            //draw box around image area
            int offset = (int) getResources().getDimension(R.dimen.selected_strokewidth)/2;
            rect.left += offset;
            rect.right -= offset;
            rect.top += offset;
            rect.bottom -= offset;
            canvas.drawRect(rect,contourPaint);
        }
    }
}
