package vektah.rust.ide.debugger;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.xdebugger.XDebuggerUtil;
import com.intellij.xdebugger.breakpoints.XLineBreakpointType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import vektah.rust.RustFileType;
import vektah.rust.psi.*;

import java.util.List;

public abstract class RustLineBreakpointTypeBase<T extends RustBreakpointProperties> extends XLineBreakpointType<T> {
	protected RustLineBreakpointTypeBase(@NonNls @NotNull String id, @Nls @NotNull String title) {
		super(id, title);
	}

	@Override
	public boolean canPutAt(@NotNull final VirtualFile file, final int line, @NotNull final Project project) {
		final PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
		if (psiFile == null || !RustFileType.INSTANCE.equals(psiFile.getFileType())) {
			return false;
		}


		final Document document = FileDocumentManager.getInstance().getDocument(file);
		if (document == null) {
			return false;
		}
		final Ref<Class<? extends RustLineBreakpointTypeBase>> result = Ref.create();
		XDebuggerUtil.getInstance().iterateLine(project, document, line, new Processor<PsiElement>() {
			@Override
			public boolean process(PsiElement element) {
				// avoid comments
				if ((element instanceof PsiWhiteSpace) || (PsiTreeUtil.getParentOfType(element, PsiComment.class, false) != null)) {
					return true;
				}
				PsiElement parent = element;
				while (element != null) {
					if (element instanceof RustAttribute) {
						element = element.getParent();
						continue;
					} else if (element instanceof PsiFile) {
						return true;
					}

					final int offset = element.getTextOffset();
					if (offset >= 0) {
						if (document.getLineNumber(offset) != line) {
							break;
						}
					}
					parent = element;
					element = element.getParent();
				}

				if (!(parent instanceof RustFnItem) &&
						!(parent instanceof RustFnDeclaration) &&
						(parent.getClass() != LeafPsiElement.class))
				{
					for (PsiElement psiElement : parent.getChildren()) {
						if (document.getLineNumber(psiElement.getTextOffset()) == line) {
							result.set(RustLineBreakpointType.class);
							return true;
						}
					}
				}
				return true;
			}
		});
		return result.get() == getClass();
	}
}
