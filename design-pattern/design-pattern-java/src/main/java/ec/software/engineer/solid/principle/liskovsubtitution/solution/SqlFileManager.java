package ec.software.engineer.solid.principle.liskovsubtitution.solution;

import java.util.List;

public class SqlFileManager {

	public String getTextFormFile(List<IReadableSqlFile> iReadableSqlFiles) {
		StringBuilder stringBuilder = new StringBuilder();

		for (IReadableSqlFile iReadableSqlFile : iReadableSqlFiles) {
			stringBuilder.append(iReadableSqlFile.LoadText());
		}
		return stringBuilder.toString();
	}

	public void saveTextIntoFiles(List<IWritableSqlFile> iWritableSqlFiles) {

		for (IWritableSqlFile iWritableSqlFile : iWritableSqlFiles) {
			iWritableSqlFile.saveText();
		}
	}

}
