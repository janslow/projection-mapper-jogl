package com.jayanslow.projection.jogl.painter;

public abstract class AbstractPainter<T> implements Painter<T> {

	private final PainterFactory	factory;
	private final Class<T>			type;

	public AbstractPainter(Class<T> type, PainterFactory factory) {
		this.factory = factory;
		this.type = type;
	}

	protected PainterFactory getFactory() {
		return factory;
	}

	@Override
	public Class<T> getTargetClass() {
		return type;
	}

}
