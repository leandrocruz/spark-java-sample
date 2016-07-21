package sample.http.analyser;

import javaslang.control.Either;
import sample.http.commons.UrlData;

public interface UrlAnalyser
{
	/*
	 * Analyses urls returning the data as UrlData or the error status code 
	 */
	Either<Integer, UrlData> analyse(String url)
		throws UrlAnalyserException;
}
