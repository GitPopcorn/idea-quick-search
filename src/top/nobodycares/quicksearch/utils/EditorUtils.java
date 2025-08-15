package top.nobodycares.quicksearch.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.editor.FoldRegion;
import com.intellij.openapi.editor.FoldingModel;
import org.jetbrains.annotations.NotNull;

/**
 * Editor Utilities
 * @author ZhouYi
 * @date 2023/03/27 17:04
 * @note note
 */
public class EditorUtils {
	
	// ===== ===== ===== ===== [Static Utility Methods-Basic Editor Opeartion] ===== ===== ===== ===== //
	
	/**
	 * Get and check required data from {@link AnActionEvent}, never return null
	 * @reference AnActionEvent#getRequiredData(DataKey) in [IntelliJ IDEA IU-252.23892.409] 
	 * @throws AssertionError if data is missing
	 * @param event Event object
	 * @param key The data key for the data to be retrieved
	 * @return {@code @NotNull T} The data retrieved from the event, never null
	 * @author ZhouYi
	 * @date 2025/08/15 12:38
	 * @note This method is copied from AnActionEvent#getRequiredData(DataKey) in [IntelliJ IDEA IU-252.23892.409].
	 *       As the original method will be removed in future versions, this method is provided to ensure compatibility.
	 * @note note
	 */
	public static <T> @NotNull T getRequiredData(@NotNull AnActionEvent event, @NotNull DataKey<T> key) {
		T data = event.getData(key);
		if (data == null) {
			throw new AssertionError(key.getName() + " is missing");
			
		} else {
			return data;
			
		}
		
	}
	
	// ===== ===== ===== ===== [Static Utility Methods-Expand Folded Region] ===== ===== ===== ===== //
	
	/**
	 * Expand all folded regions crossed by range
	 * @param foldingModel folding model
	 * @param rangeStart The start offset of range
	 * @param rangeEnd The end offset of range
	 * @return {@code void}
	 * @author ZhouYi
	 * @date 2023/03/27 17:07
	 * @note note
	 */
	public static void expandAllFoldedRegionsCrossedByRange(FoldingModel foldingModel, int rangeStart, int rangeEnd) {
		// STEP Number Expand all folded regions at range start and range end
		expandCollapsedRegionsAtOffsets(foldingModel, rangeStart, rangeEnd);
		
		// STEP Number Expand all folded regions cross the range
		// expandAllCollapsedRegionsCrossRange(foldingModel, rangeStart, rangeEnd);
		
	}
	
	/**
	 * Find the collapsed regions at positions and expand them
	 * @param foldingModel folding model
	 * @param positions the positions to check
	 * @return {@code void}
	 * @author ZhouYi
	 * @date 2023/03/27 17:15
	 * @note note
	 */
	public static void expandCollapsedRegionsAtOffsets(FoldingModel foldingModel, int... positions) {
		for (int pos : positions) {
			FoldRegion collapsedRegionAtOffset1 = foldingModel.getCollapsedRegionAtOffset(pos);
			if (collapsedRegionAtOffset1 != null) { collapsedRegionAtOffset1.setExpanded(true); }
			
		}
		
	}
	
	/**
	 * Find the collapsed regions cross specific range and expand them
	 * @param foldingModel folding model
	 * @param rangeStart The start offset of range
	 * @param rangeEnd The end offset of range
	 * @return {@code void}
	 * @author ZhouYi
	 * @date 2023/03/27 17:15
	 * @note note
	 */
	public static void expandAllCollapsedRegionsCrossRange(FoldingModel foldingModel, int rangeStart, int rangeEnd) {
		FoldRegion[] allFoldRegions = foldingModel.getAllFoldRegions();
		for (FoldRegion region : allFoldRegions) {
			if ((!region.isExpanded()) && isRangeCrossed(region.getStartOffset(), region.getEndOffset(), rangeStart, rangeEnd)) {
				region.setExpanded(true);
				
			}
			
		}
		
	}
	
	// ===== ===== ===== ===== [Static Utility Methods-Range Judgement] ===== ===== ===== ===== //
	
	/**
	 * Determine if range 1 is crossed by range 2
	 * @param start1 the start offset of range 1
	 * @param end1 the end offset of range 1
	 * @param start2 the start offset of range 2
	 * @param end2 the end offset of range 2
	 * @return {@code boolean} true if range 1 is crossed by range 2, otherwise false
	 * @author ZhouYi
	 * @date 2023/03/27 17:12
	 * @note note
	 */
	public static boolean isRangeCrossed(int start1, int end1, int start2, int end2) {
		return ! ((end1 < start2) || (start1 > end2));
		
	}
	
}
