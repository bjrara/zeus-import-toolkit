package com.ctrip.zeus;

import com.ctrip.zeus.model.RewriteRule;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhoumy on 2015/6/11.
 */
public class RewriteRulesWriter {
    public static void writeToExcel(List<RewriteRule> rewriteRuleList) throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Rewrite rules");
        // Set headers
        Row headers = sheet.createRow(0);
        headers.createCell(0).setCellValue("Rule name");
        headers.createCell(1).setCellValue("Match");
        headers.createCell(2).setCellValue("Action");

        for (int i = 1; i <= rewriteRuleList.size(); i++) {
            RewriteRule r = rewriteRuleList.get(i - 1);
            Row record = sheet.createRow(i);
            record.createCell(0).setCellValue(r.getRuleName());
            record.createCell(1).setCellValue(r.getIn());
            record.createCell(2).setCellValue(r.getOut());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream("rewrite-rules.xls");
            wb.write(fo);
        } finally {
            if (fo == null)
                fo.close();
        }
    }
}
