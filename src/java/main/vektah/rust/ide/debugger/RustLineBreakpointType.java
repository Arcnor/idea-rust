package vektah.rust.ide.debugger;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.breakpoints.XLineBreakpointType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vektah.rust.i18n.RustBundle;

public class RustLineBreakpointType extends XLineBreakpointType<RustLineBreakpointProperties> {
	protected RustLineBreakpointType() {
		super("rust-line", RustBundle.message("debugger.breakpoint.line.tab.title"));
	}

	@Nullable
	@Override
	public RustLineBreakpointProperties createBreakpointProperties(@NotNull VirtualFile file, int line) {
		return new RustLineBreakpointProperties();
	}
}
