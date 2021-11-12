package top.nobodycares.quicksearch.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import top.nobodycares.quicksearch.utils.StringUtils;

/**
 * Action - Find and move cursor to previous match of current selection
 * @author ZhouYi
 * @date 2021/11/12 16:29
 * @description description
 * @note note
 */
public class PreviousOccurrenceOfSelection extends AnAction {
	
	// ===== ===== ===== ===== [Operation Methods] ===== ===== ===== ===== //
	
	@Override
	public void actionPerformed(AnActionEvent e) {
		// STEP Number Get editor, caret, selection, selected text and verify
		final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
		CaretModel caretModel = editor.getCaretModel();
		SelectionModel selectionModel = editor.getSelectionModel();
		String selectedText = selectionModel.getSelectedText();
		if (StringUtils.isEmpty(selectedText)) {
			return;
			
		}
		if (caretModel.getCaretCount() != 1) {
			return;
			
		}
		
		// STEP Number Find previous occurrence of selected text wrapped from current selection start forwardly
		String documentText = editor.getDocument().getText();
		int fromIndex = selectionModel.getSelectionStart();
		int occur = -2;
		// CONDITION Number If there is not any occurrence before, straightly searched from ending
		if (fromIndex < selectedText.length()) {
			occur = documentText.lastIndexOf(selectedText);
			
		// CONDITION Number If there may be occurrence before, searched from current position,
		//                  then searched from ending if not found
		} else {
			occur = documentText.lastIndexOf(selectedText, fromIndex - 1);
			if (occur < 0) {
				occur = documentText.lastIndexOf(selectedText);
				
			}
			
		}
		if (occur < 0) {
			return;
			
		}
		
		// STEP Number Change caret and selection to target position if found
		int endOffset = occur + selectedText.length();
		selectionModel.setSelection(occur, endOffset);
		caretModel.moveToOffset(endOffset);
		
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
