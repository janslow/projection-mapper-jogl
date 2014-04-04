package com.jayanslow.projection.jogl.painter;

import java.util.Map;

import javax.media.opengl.GL2;

import com.jayanslow.projection.jogl.RenderMode;

public class MapPainterFactory implements PainterFactory {

	private final Map<Class<?>, Painter<?>>	map;

	public MapPainterFactory(Map<Class<?>, Painter<?>> map) {
		this.map = map;
	}

	@Override
	public <T> boolean addPainter(Painter<T> painter) {
		Painter<?> existing = map.get(painter.getTargetClass());
		if (existing != null && existing.getClass().equals(painter.getClass()))
			return false;
		map.put(painter.getTargetClass(), painter);
		return true;
	}

	@Override
	public <T> boolean containsPainter(Class<T> type) {
		return map.containsKey(type);
	}

	@SuppressWarnings("unchecked")
	protected <T> Painter<T> getPainter(final Class<T> type) {
		final Painter<T> t = (Painter<T>) map.get(type);
		if (t == null)
			throw new RuntimeException("No painter for type in factory: " + type.getCanonicalName());
		return t;
	}

	@Override
	public <T> void paint(GL2 gl, Class<T> type, T t, RenderMode renderType) {
		getPainter(type).paint(gl, t, renderType);
	}
}
