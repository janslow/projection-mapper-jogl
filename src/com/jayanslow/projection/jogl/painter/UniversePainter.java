package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;

import com.jayanslow.projection.jogl.RenderMode;
import com.jayanslow.projection.world.models.CuboidUniverse;
import com.jayanslow.projection.world.models.Universe;

public class UniversePainter extends AbstractPainter<Universe> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new UniversePainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f)) {
			CuboidUniversePainter.registerNested(f);
			RealObjectPainter.registerNested(f);
		}
	}

	public UniversePainter(PainterFactory factory) {
		super(Universe.class, factory);
	}

	@Override
	public void paint(GL2 gl, Universe t, RenderMode renderType) {

		getFactory().paint(gl, CuboidUniverse.class, (CuboidUniverse) t, renderType);
	}
}
