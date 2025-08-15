package top.nobodycares.quicksearch.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import top.nobodycares.quicksearch.utils.EditorUtils;
import top.nobodycares.quicksearch.utils.StringUtils;

/**
 * Action - Find and move cursor to next match of current selection
 * @author ZhouYi
 * @date 2021/11/12 16:29
 * @description description
 * @note note
 */
@SuppressWarnings("MissingActionUpdateThread")
public class NextOccurrenceOfSelection extends AnAction {
	
	// ===== ===== ===== ===== [Operation Methods] ===== ===== ===== ===== //
	
	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		// STEP Number Get editor, caret, selection, folding block, selected text and verify
		
		// CHANGED 2025/08/15 12:41 BY.ZhouYi Replace the will-be-removed API [AnActionEvent#getRequiredData(DataKey)]
		// final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
		final Editor editor = EditorUtils.getRequiredData(e, CommonDataKeys.EDITOR);
		
		CaretModel caretModel = editor.getCaretModel();
		SelectionModel selectionModel = editor.getSelectionModel();
		// FoldingModel foldingModel = editor.getFoldingModel();
		String selectedText = selectionModel.getSelectedText();
		if (StringUtils.isEmpty(selectedText)) {
			return;
			
		}
		if (caretModel.getCaretCount() != 1) {
			return;
			
		}
		
		// STEP Number Find next occurrence of selected text wrapped from current selection end
		String documentText = editor.getDocument().getText();
		int fromIndex = selectionModel.getSelectionEnd();
		int occur = documentText.indexOf(selectedText, fromIndex);
		if (occur < 0) {
			occur = documentText.indexOf(selectedText);
			
		}
		if (occur < 0) {
			return;
			
		}
		
		// STEP Number Calculate the new end offset
		int endOffset = occur + selectedText.length();
		
		// STEP Number Determine if there is any folded blocks cross the new occurrence, if is, expand them
		// NOTE Number The editor will expand the folded blocks automatically when the caret is moved to them, so this step is not needed
		// EditorUtils.expandAllFoldedRegionsCrossedByRange(foldingModel, occur, endOffset);
		
		// STEP Number Change caret and selection to target position if found
		// NOTE Number Move caret before setting selection, or the folded blocks at new caret position will not expand normally
		caretModel.moveToOffset(endOffset);
		selectionModel.setSelection(occur, endOffset);
		
		// STEP Number Scroll the visual area to new logical area
		editor.getScrollingModel().scrollToCaret(ScrollType.MAKE_VISIBLE);
		
		// TODO Number [Pseudo Code] Reading the user settings to decide if to record the selected text into FINDING HISTORY
		/*
		if (isHistoryRecordNeeded()) {
			e.getRequiredData(CommonDataKeys.FINDING).getFindingModel().getHistory().add(selectedText);
			
		}
		*/
		
	}
	
	/**
	 * Sets visibility and enables this action menu item if:
	 * <ul>
	 *   <li>a project is open</li>
	 *   <li>an editor is active</li>
	 *   <li>some characters are selected</li>
	 * </ul>
	 *
	 * @param e Event related to this action
	*/
	@Override
	public void update(@NotNull final AnActionEvent e) {
		// Get required data keys
		final Project project = e.getProject();
		final Editor editor = e.getData(CommonDataKeys.EDITOR);
		// Set visibility and enable only in case of existing project and editor and if a selection exists
		// CHANGED Number Just disable this action when invalid, not to hide it.
		// e.getPresentation().setEnabledAndVisible(
		e.getPresentation().setEnabled(
				(project != null) && (editor != null) && editor.getSelectionModel().hasSelection()
		);
		
	}
	
	// ===== ===== ===== ===== [Static Utility Methods] ===== ===== ===== ===== //
	
	
	
}
