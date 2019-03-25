package com.frixs.zcu_kiv_mkz_seminar.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.frixs.zcu_kiv_mkz_seminar.R;
import com.frixs.zcu_kiv_mkz_seminar.enums.TileType;

public class GameView extends View {
    private Paint mPaint = new Paint();
    private TileType viewMap[][];

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewMap(TileType[][] map) {
        this.viewMap = map;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (viewMap == null) {
            Log.e("!" + getClass(), "No view map set!");
            return;
        }


        // Set view resolution.
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.height = canvas.getWidth();
        this.setLayoutParams(params);

        // Get tile size.
        float tileSizeX = canvas.getWidth() / (float) viewMap.length;
        float tileSizeY = canvas.getHeight() / (float) viewMap[0].length;

        // Vars.
        int squarePadding = 0;
        float circleSize = Math.min(tileSizeX, tileSizeY) / 2;

        // Set map background.
        mPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorTileBackground, null));
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);

        for (int x = 0; x < viewMap.length; x++) {
            for (int y = 0; y < viewMap[0].length; y++) {
                squarePadding = 2; // default;

                // Tile type draw settings.
                switch (viewMap[x][y]) {
                    case None:
                        mPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorTileNone, null));
                        break;
                    case Wall:
                        squarePadding = 0;
                        mPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorTileWall, null));
                        break;
                    case SnakeHead:
                        mPaint.setColor(Color.RED);
                        break;
                    case SnakeTail:
                        mPaint.setColor(Color.GREEN);
                        break;
                    case Apple:
                        mPaint.setColor(Color.RED);
                        break;
                }

                float x1 = x * tileSizeX + squarePadding;
                float y1 = y * tileSizeY + squarePadding;
                float x2 = x * tileSizeX + tileSizeX - squarePadding;
                float y2 = y * tileSizeY + tileSizeY - squarePadding;

                // Symmetric margins.
                x1 = (x == 1) ? x1 + squarePadding : x1;
                y1 = (y == 1) ? y1 + squarePadding : y1;
                x2 = (x == viewMap.length - 2) ? x2 - squarePadding : x2;
                y2 = (y == viewMap[0].length - 2) ? y2 - squarePadding : y2;

                canvas.drawRect(x1, y1, x2, y2, mPaint);
            }
        }
    }
}
