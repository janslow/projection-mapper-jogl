package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.world.models.Projector;
import com.jayanslow.projection.world.models.RealObject;
import com.jayanslow.projection.world.models.RenderMode;
import com.jayanslow.projection.world.models.Screen;

public class RealObjectPainter extends AbstractPainter<RealObject> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new RealObjectPainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f)) {
			ScreenPainter.registerNested(f);
			ProjectorPainter.registerNested(f);
		}
	}

	public RealObjectPainter(PainterFactory factory) {
		super(RealObject.class, factory);
	}

	@Override
	public void paint(GL2 gl, RealObject t, RenderMode renderType) {
		switch (t.getType()) {
		case PROJECTOR:
			getFactory().paint(gl, Projector.class, (Projector) t, renderType);
			break;
		case SCREEN:
			getFactory().paint(gl, Screen.class, (Screen) t, renderType);
			break;
		default:
			throw new RuntimeException("Unhandled RealObjectType in RealObjectPainter.paint");
		}
	}
}
