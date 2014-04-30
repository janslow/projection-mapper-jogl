package com.jayanslow.projection.jogl;

import java.awt.Frame;
import java.io.File;
import java.util.HashMap;

import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.RenderMode.FaceMode;
import com.jayanslow.projection.jogl.painter.MapPainterFactory;
import com.jayanslow.projection.jogl.painter.OriginPainter;
import com.jayanslow.projection.jogl.painter.Painter;
import com.jayanslow.projection.jogl.painter.PainterFactory;
import com.jayanslow.projection.jogl.painter.UniversePainter;
import com.jayanslow.projection.texture.controllers.TextureController;
import com.jayanslow.projection.texture.listeners.TextureListener;
import com.jayanslow.projection.texture.models.Texture;
import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.listeners.WorldListener;
import com.jayanslow.projection.world.models.Rotation3f;

public abstract class VisualiserFrame extends Frame implements CameraListener, WorldListener, TextureListener {

	private static final long	serialVersionUID	= -544813390445755709L;

	static PainterFactory setUpPainterFactory(TextureController textures) {
		PainterFactory f = new MapPainterFactory(new HashMap<Class<?>, Painter<?>>(), textures);

		UniversePainter.registerNested(f);
		OriginPainter.registerNested(f);

		return f;
	}

	private final WorldController	world;
	private final PainterFactory	f;

	private volatile boolean		markDirty		= false;
	protected File					outputFile;

	protected boolean				saveNextFrame	= false;

	public VisualiserFrame(WorldController world, PainterFactory f, String title) {
		super(title);
		this.f = f;
		this.world = world;

		world.addWorldListener(this);
	}

	@Override
	public void cameraChangeFieldOfView(Camera camera, float old) {
		markClean();
	}

	@Override
	public void cameraChangeResolution(Camera camera, int oldHeight, int oldWidth) {
		markDirty();
	}

	@Override
	public void cameraMove(Camera camera, Vector3f old) {}

	@Override
	public void cameraRotate(Camera camera, Rotation3f old) {}

	public abstract Camera getCamera();

	public abstract float getFPS();

	public PainterFactory getPainterFactory() {
		return f;
	}

	public abstract RenderMode getRenderMode();

	public WorldController getWorldController() {
		return world;
	}

	protected synchronized boolean isMarkedDirty() {
		return markDirty;
	}

	protected synchronized void markClean() {
		markDirty = false;
	}

	protected synchronized void markDirty() {
		markDirty = true;
	}

	@Override
	public void worldChanged() {
		markDirty();
	}

	@Override
	public void textureChange(Texture texture) {
		if (getRenderMode().getFaceMode().equals(FaceMode.TEXTURED))
			markDirty();
	}

	@Override
	public void textureFrameChange(int current, int old) {
		if (getRenderMode().getFaceMode().equals(FaceMode.TEXTURED))
			markDirty();
	}
}
