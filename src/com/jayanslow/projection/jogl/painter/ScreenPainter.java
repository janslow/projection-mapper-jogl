package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.world.models.CuboidScreen;
import com.jayanslow.projection.world.models.FlatScreen;
import com.jayanslow.projection.world.models.RenderMode;
import com.jayanslow.projection.world.models.Screen;

public class ScreenPainter extends AbstractPainter<Screen> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new ScreenPainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f)) {
			FlatScreenPainter.registerNested(f);
			CuboidScreenPainter.registerNested(f);
		}
	}

	public ScreenPainter(PainterFactory factory) {
		super(Screen.class, factory);
	}

	@Override
	public void paint(GL2 gl, Screen t, RenderMode renderType) {
		switch (t.getScreenType()) {
		case FLAT:
			getFactory().paint(gl, FlatScreen.class, (FlatScreen) t, renderType);
			break;
		case CUBOID:
			getFactory().paint(gl, CuboidScreen.class, (CuboidScreen) t, renderType);
			break;
		default:
			throw new RuntimeException("Unhandled ScreenType in ScreenPainter.paint");
		}
	}
}
