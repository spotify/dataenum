package com.spotify.dataenum.processor;

import com.spotify.dataenum.dataenum_case;
import com.sun.source.util.Trees;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public final class ProcessingContext {
  public final ProcessingEnvironment env;
  public final Trees trees;
  public final TypeMirror dataenum_class_element;

  public ProcessingContext(ProcessingEnvironment env) {
    this.env = env;
    this.trees = Trees.instance(env);

    Types types = env.getTypeUtils();
    Elements elements = env.getElementUtils();
    this.dataenum_class_element =
        elements.getTypeElement(dataenum_case.class.getCanonicalName()).asType();
  }
}
