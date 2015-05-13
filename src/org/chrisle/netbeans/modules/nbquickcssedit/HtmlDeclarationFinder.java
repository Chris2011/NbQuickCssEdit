package org.chrisle.netbeans.modules.nbquickcssedit;

import javax.swing.text.Document;
import org.netbeans.modules.csl.api.DeclarationFinder;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.editor.NbEditorUtilities;

/**
 *
 * @author chrl
 */
public class HtmlDeclarationFinder implements DeclarationFinder {
    private static final String XHTML_MIMETYPE = "text/xhtml"; //NOI18N

    @Override
    public DeclarationFinder.DeclarationLocation findDeclaration(ParserResult info, int caretOffset) {
//        for (HtmlExtension ext : HtmlExtension.getRegisteredExtensions(info.getSnapshot().getSource().getMimeType())) {
//            DeclarationFinder.DeclarationLocation loc = ext.findDeclaration(info, caretOffset);
//            if (loc != null) {
//                return loc;
//            }
//        }
        return DeclarationFinder.DeclarationLocation.NONE;
    }

    @Override
    public OffsetRange getReferenceSpan(Document dcmnt, int caretOffset) {
        String mimeType = NbEditorUtilities.getMimeType(dcmnt);
//        for (HtmlExtension ext : HtmlExtension.getRegisteredExtensions(mimeType)) {
//            OffsetRange range = ext.getReferenceSpan(dcmnt, caretOffset);
//            if (range != null) {
//                return range;
//            }
//        }
        return OffsetRange.NONE;
    }
}
