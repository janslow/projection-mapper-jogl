package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.world.models.Face;
import com.jayanslow.projection.world.models.RectangularFace;
import com.jayanslow.projection.world.models.RenderMode;

public class FacePainter extends AbstractPainter<Face> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new FacePainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f))
			RectangularFacePainter.registerNested(f);
	}

	public FacePainter(PainterFactory factory) {
		super(Face.class, factory);
	}

	@Override
	public void paint(GL2 gl, Face t, RenderMode renderType) {
		getFactory().paint(gl, RectangularFace.class, (RectangularFace) t, renderType);
	}
}
