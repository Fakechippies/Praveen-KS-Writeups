package pl.droidsonroids.gif;

import android.graphics.Canvas;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import pl.droidsonroids.gif.GifTextureView;

/* loaded from: classes.dex */
class PlaceholderDrawingSurfaceTextureListener implements TextureView.SurfaceTextureListener {
    private final GifTextureView.PlaceholderDrawListener mDrawer;

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PlaceholderDrawingSurfaceTextureListener(GifTextureView.PlaceholderDrawListener placeholderDrawListener) {
        this.mDrawer = placeholderDrawListener;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Surface surface = new Surface(surfaceTexture);
        Canvas lockCanvas = surface.lockCanvas(null);
        this.mDrawer.onDrawPlaceholder(lockCanvas);
        surface.unlockCanvasAndPost(lockCanvas);
        surface.release();
    }
}
