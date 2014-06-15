package vektah.rust.ide.debugger;

import com.intellij.xdebugger.breakpoints.XBreakpointProperties;
import org.jetbrains.annotations.Nullable;

public class RustBreakpointProperties<T extends RustBreakpointProperties> extends XBreakpointProperties<T> {

	@Nullable
	@Override
	public T getState() {
		return (T)this;
	}

	@Override
	public void loadState(T state) {

	}
}
