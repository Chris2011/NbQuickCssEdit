/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chrisle.netbeans.modules.nbquickcssedit;

import java.util.List;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
import org.netbeans.editor.BaseDocument;
import org.netbeans.editor.JumpList;
import org.netbeans.editor.ext.ExtSyntaxSupport;
import org.netbeans.spi.editor.AbstractEditorAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.netbeans.editor.Utilities;
import org.openide.util.Exceptions;
import org.netbeans.api.editor.NavigationHistory;
import org.netbeans.api.editor.NavigationHistory.Waypoint;

//@ActionID(
//        category = "Edit",
//        id = "org.chrisle.netbeans.modules.nbquickcssedit.QuickCssEdit"
//)
@ActionID(
        category = "Edit",
        id = "org.chrisle.netbeans.modules.nbquickcssedit.QuickCssEdit"
)
@ActionRegistration(
        displayName = "#CTL_QuickCssEdit"
)
@ActionReferences({
//    @ActionReference(path = "Menu/Edit", position = 100),
    @ActionReference(path = "Editors/text/html/Popup", position = 1295)
})
@Messages("CTL_QuickCssEdit=Quick CSS Edit")

//@ActionRegistrations({
//    @ActionRegistration(
//        displayName = "Quick CSS Edit"
////        menuPath = "GoTo",
////        menuPosition = 900,
////        menuText = "Quick CSS Edit"
//    )
//})
//@EditorActionReferences({
//    @EditorActionReference(path = "Menu/Edit", position = 100),
//    @EditorActionReference(path = "Editors/text/html/Popup", position = 1295)
//})
//@Messages("CTL_QuickCssEdit=Quick CSS Edit")
public final class QuickCssEdit extends AbstractEditorAction {

    @Override
    public void actionPerformed(ActionEvent ev, JTextComponent target) {
        resetCaretMagicPosition(target);
        JDialog tt = new JDialog();
        final JLabel ll = new JLabel();

        if (target != null) {
//            if (findDeclarations(target)) {
//                return;
//            }

            BaseDocument doc = Utilities.getDocument(target);
            if (doc != null) {
                try {
                    Caret caret = target.getCaret();
                    int position = target.getCaretPosition();
                    int dotPos = caret.getDot();
                    int[] idBlk = Utilities.getIdentifierBlock(doc, dotPos);

                    ExtSyntaxSupport extSup = (ExtSyntaxSupport) doc.getSyntaxSupport();

                    try {
                        final NavigationHistory navigations = NavigationHistory.getNavigations() //                    label.setText(Utilities.getWord(component, position));
                                ;
                        navigations.markWaypoint(target, position, false, false);

                        JumpList.addEntry(target, position);
                        ll.setText(dump());

//                    if (navigations.hasNextWaypoints()) {
//                        navigations.getNextWaypoints().forEach(new Consumer<NavigationHistory.Waypoint>() {
//
//                            @Override
//                            public void accept(NavigationHistory.Waypoint t) {
//                                ll.setText(t.getComponent().getText());
//                            }
//                        });
//                    }
                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                    // TODO: Refactor to new Lexer one.
//                    LexerRestartInfo<HTMLTokenId> test = new LexerRestartInfo<HTMLTokenId>;
//                    HtmlLexer test = new HtmlLexer(ll);
                    if (idBlk != null) {
                        int decPos = extSup.findDeclarationPosition(doc.getText(idBlk), idBlk[1]);

                        tt.add(ll);
                        tt.setVisible(true);

                        if (decPos >= 0) {
                            caret.setDot(decPos);
                        }
                    }
                } catch (BadLocationException e) {
                }
            }
        }

//        Language lang = LanguageRegistry.getInstance().getLanguageByMimeType("text/xhtml");
//        DeclarationFinder declarationFinder = lang.getDeclarationFinder();
//
//        declarationFinder.findDeclaration(null, 0);
    }

    public static String dump() {
        StringBuilder sb = new StringBuilder();

        sb.append("Previous waypoints: {\n"); //NOI18N
        List<Waypoint> prev = NavigationHistory.getNavigations().getPreviousWaypoints();
        for (NavigationHistory.Waypoint wpt : prev) {
            URL url = wpt.getUrl();
            sb.append("    ").append(url.toString() + ":" + wpt.getOffset()).append("\n"); //NOI18N
        }
        sb.append("}\n"); //NOI18N

        sb.append("Next waypoints: {\n"); //NOI18N
        List<Waypoint> next = NavigationHistory.getNavigations().getNextWaypoints();
        for (NavigationHistory.Waypoint wpt : next) {
            URL url = wpt.getUrl();
            sb.append("    ").append(url.toString()).append("\n"); //NOI18N
        }
        sb.append("}\n"); //NOI18N

        return sb.toString();
    }

//    private boolean findDeclarations(JTextComponent component) {
//        JDialog test = new JDialog();
//        test.setSize(120, 120);
//        JTextField text = new JTextField();
//        Document doc = component.getDocument();
//        Object mimeTypeObj = doc.getProperty(BaseDocument.MIME_TYPE_PROP);  //NOI18N
//        String mimeType;
//
//        if (mimeTypeObj instanceof String) {
//            mimeType = (String) mimeTypeObj;
//        } else {
//            return false;
//        }
//
//        int position = component.getCaretPosition();
//        Lookup lookup = MimeLookup.getLookup(mimeType);
//
//        for (HyperlinkProviderExt provider : lookup.lookupAll(HyperlinkProviderExt.class)) {
//            if (provider.getSupportedHyperlinkTypes().contains(HyperlinkType.GO_TO_DECLARATION) && provider.isHyperlinkPoint(doc, position, HyperlinkType.GO_TO_DECLARATION)) {
////                JumpList.addEntry(component, position);
//                final JLabel label = new JLabel();
//
//                try {
//                    final NavigationHistory navigations = NavigationHistory.getNavigations()
////                    label.setText(Utilities.getWord(component, position));
//;
//                    navigations.markWaypoint(component, position, false, false);
//
//                    if (navigations.hasNextWaypoints()) {
//                        navigations.getNextWaypoints().forEach(new Consumer<NavigationHistory.Waypoint>() {
//
//                            @Override
//                            public void accept(NavigationHistory.Waypoint t) {
//                                label.setText(t.getComponent().getText());
//                            }
//                        });
//                    }
//                    
//                } catch (BadLocationException ex) {
//                    Exceptions.printStackTrace(ex);
//                }
//
////                try {
////                    NavigationHistory.Waypoint markWaypoint = NavigationHistory.getNavigations().markWaypoint(component, position, false, false);
////                    
//////                    label.setText(NavigationHistory.getNavigations().getCurrentWaypoint().getComponent().getText());
////                } catch (BadLocationException ex) {
////                    Exceptions.printStackTrace(ex);
////                }
//                test.add(label);
//                test.setVisible(true);
////                provider.performClickAction(doc, position, HyperlinkType.GO_TO_DECLARATION);
//                return true;
//            }
//        }
//
//        for (final HyperlinkProvider provider : lookup.lookupAll(HyperlinkProvider.class)) {
//            if (provider.isHyperlinkPoint(doc, position)) {
//                //make sure the JumpList works:
//                JumpList.addEntry(component, position);
//
////                text.setText(component.getName());
////                test.add(text);
////                test.setVisible(true);
//                provider.performClickAction(doc, position);
//                return true;
//            }
//        }
//
//        return false;
//    }
}
