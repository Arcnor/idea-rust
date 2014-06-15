package vektah.rust.ide.debugger;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.breakpoints.XLineBreakpointType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RustLineBreakpointTypeBase<T extends RustBreakpointProperties> extends XLineBreakpointType<T> {
	protected RustLineBreakpointTypeBase(@NonNls @NotNull String id, @Nls @NotNull String title) {
		super(id, title);
	}
}
