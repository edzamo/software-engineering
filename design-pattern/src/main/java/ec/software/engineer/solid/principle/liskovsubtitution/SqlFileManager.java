package ec.software.engineer.solid.principle.liskovsubtitution;

import java.util.List;

public class SqlFileManager {

	private List<SqlFile> sqlFiles;

	public String getTextFormFile() {
		StringBuilder stringBuilder = new StringBuilder();
		for (SqlFile sqlFile : sqlFiles) {

			stringBuilder.append(sqlFile.loadText());
		}
		return stringBuilder.toString();
	}

	public void saveTextIntoFiles() {
		for (SqlFile sqlFile : sqlFiles) {

			sqlFile.saveText();
		}
	}

}
