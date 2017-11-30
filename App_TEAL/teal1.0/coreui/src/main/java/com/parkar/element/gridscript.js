function GetUIGridCellValue(element, intRowIndex, intColumnIndex) {
	var scope = angular.element(element).scope();
	var rows = scope.grid.rows
	var columns = scope.grid.columns;

	// get the correct column index in backend
	intCIndex = GetColumnIndexInBackEnd(scope, intColumnIndex);

	if (intCIndex != -1) {
		return scope.grid.getCellValue(rows[intRowIndex], columns[intCIndex]);
	} else {
		console.log("UI grid extensibility GetUIGridCellValue error: columnIndex is incorrect.")
	}
	return "";
}

function GetColumnIndexInBackEnd(scope, intColumnIndex) {
	var columns = scope.grid.columns;
	var visibleIndex = -1;

	for (intIndex = 0; intIndex <= columns.length - 1; intIndex++) {
		var columnVisible = columns[intIndex].visible;
		if (columnVisible) {
			visibleIndex++;
			if (visibleIndex == intColumnIndex) {
				return intIndex;
			}
		}
	} 

	return -1;
}

function GetUIGridColumnNames(element) {
	var scope = angular.element(element).scope();
	var columns = scope.grid.columns;
	var columnNames = "";

	for (intIndex = 0; intIndex <= columns.length - 1; intIndex++) {
		var columnVisible = columns[intIndex].visible;
		if (columnVisible) {
			var columnName = columns[intIndex].displayName;
			columnNames += columnName + "|";
		}
	}
	// remove the last |
	if (columnNames.length > 0) {
		columnNames = columnNames.substring(0, columnNames.length - 1);
	}
	return columnNames;
}
