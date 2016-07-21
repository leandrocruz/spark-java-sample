package sample.http.parser.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import sample.http.commons.Heading;
import sample.http.commons.ImmutableHeading;
import sample.http.commons.ImmutableLink;
import sample.http.commons.ImmutableUrlData;
import sample.http.commons.Link;
import sample.http.commons.UrlData;
import sample.http.parser.DocumentParser;
import xingu.lang.NotImplementedYet;

/*
 * This is a simple implementation of a DocumentParser based on JSoup.
 * If performance is a concern, other DocumentParser should be used,
 * possibly parsing the document using a lenient sax parser so the UrlData object can be constructed is a single parsing step.
 */
public class JSoupDocumentParser
	implements DocumentParser
{
	@Override
	public UrlData parse(String input)
	{
		final Document         doc     = Jsoup.parse(input);
		final Optional<String> version = versionFrom(doc);
		final Optional<String> title   = titleFrom(doc);
	
		/* Links */
		final Elements   links       = doc.getElementsByTag("a");
		final List<Link> parsedLinks = links
				.stream()
				.map(this::toLink)
				.collect(Collectors.toList());
		
		/* Headings */
		final Elements      headings       = doc.select("h1,h2,h3,h4,h5,h6");
		final List<Heading> parsedHeadings = headings
				.stream()
				.map(this::toHeading)
				.collect(Collectors.toList());
		
		return ImmutableUrlData
				.builder()
				.version(version)
				.title(title)
				.links(parsedLinks)
				.headings(parsedHeadings)
				.build();
	}

	private Optional<String> titleFrom(Document doc)
	{
		final Elements elements = doc.getElementsByTag("title");
		if(elements.size() == 1)
		{
			return Optional.of(elements.get(0).text());
		}
		return Optional.empty();
	}

	private Optional<String> versionFrom(Document doc)
	{
		final Optional<Node> doctype = doc
				.childNodes()
				.stream()
				.filter(node -> "#doctype".equals(node.nodeName()))
				.findFirst();

		if(!doctype.isPresent())
		{
			return Optional.empty();
		}
		
		final String node = doctype.get().toString();
		if("<!DOCTYPE html>".equalsIgnoreCase(node))
		{
			return Optional.of("5+");
		}
		throw new NotImplementedYet("Can't handle doctype: " + node);

	}

	private Heading toHeading(Element el)
	{
		final String tagName = el.tagName();
		final String text    = el.text();
		final char   level   = tagName.charAt(1);
		return ImmutableHeading
				.builder()
				.level(Character.getNumericValue(level))
				.text(text)
				.build();
	}

	private Link toLink(Element el)
	{
		final String href = el.attr("href");
		final String text = el.text();
		return ImmutableLink
				.builder()
				.href(href)
				.text(text)
				.build();
	}
}