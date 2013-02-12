import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parser{
	

public String parseInfoBox(String wikiText) {
    String INFOBOX_CONST_STR = "{{Infobox";
    int startPos = wikiText.indexOf(INFOBOX_CONST_STR);
    if(startPos < 0) return new String("");
    int bracketCount = 2;
    int endPos = startPos + INFOBOX_CONST_STR.length();
    for(; endPos < wikiText.length(); endPos++) {
      switch(wikiText.charAt(endPos)) {
        case '}':
          bracketCount--;
          break;
        case '{':
          bracketCount++;
          break;
        default:
      }
      if(bracketCount == 0) break;
    }
    if(endPos+1 >= wikiText.length()) return new String("");
    // This happens due to malformed Infoboxes in wiki text. See Issue #10
    // Giving up parsing is the easier thing to do.
    String infoBoxText = wikiText.substring(startPos, endPos+1);
    infoBoxText = stripCite(infoBoxText); // strip clumsy {{cite}} tags
    // strip any html formatting
    infoBoxText = infoBoxText.replaceAll("&gt;", ">");
    infoBoxText = infoBoxText.replaceAll("&lt;", "<");
    infoBoxText = infoBoxText.replaceAll("<ref.*?>.*?</ref>", " ");
		infoBoxText = infoBoxText.replaceAll("</?.*?>", " ");
    return new String(infoBoxText);
  }

  private String stripCite(String text) {
    String CITE_CONST_STR = "{{cite";
    int startPos = text.indexOf(CITE_CONST_STR);
    if(startPos < 0) return text;
    int bracketCount = 2;
    int endPos = startPos + CITE_CONST_STR.length();
    for(; endPos < text.length(); endPos++) {
      switch(text.charAt(endPos)) {
        case '}':
          bracketCount--;
          break;
        case '{':
          bracketCount++;
          break;
        default:
      }
      if(bracketCount == 0) break;
    }
    text = text.substring(0, startPos-1) + text.substring(endPos);
    return stripCite(text);   
  }
  public String getCategories(String text){
	  String categories = new String("");
	  Pattern pattern = Pattern.compile("\\[\\[Category:(.*?)\\]\\]");
	  Matcher matcher = pattern.matcher(text);
	  while (matcher.find()) {
	      categories = categories+" "+matcher.group().replaceAll("\\[\\[Category:","");
	    }
	  return new String(categories);
  }
  
  public String getLinks(String text){
	  String links = new String("");
	  Pattern pattern = Pattern.compile("\\[\\[(?!(Category))(.*?)\\]\\]", Pattern.MULTILINE);
	  Matcher matcher = pattern.matcher(text);
	  while (matcher.find()) {
	      links = links+" "+matcher.group();
	    }
	  return new String(links);
  }
  
}