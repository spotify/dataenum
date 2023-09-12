package com.spotify.dataenum.processor.parser;

import com.spotify.dataenum.Static;
import com.spotify.dataenum.processor.generator.method.MethodMethods;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.sun.source.util.Trees;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

public final class MethodParser {

  private static final TypeName STATIC_ANNOTATION = ClassName.get(Static.class);

  public static MethodSpec parse(ExecutableElement el, Trees trees) {
    // filter off DEFAULT modifier
    Function<Stream<Modifier>, Stream<Modifier>> adjustModifiers =
        mods -> mods.filter(x -> x != Modifier.DEFAULT);
    // populate STATIC modifier if required
    if (el.getAnnotation(Static.class) != null) {
      adjustModifiers =
          adjustModifiers.andThen(mods -> Stream.concat(Stream.of(Modifier.STATIC), mods));
    }

    // make sure internal @Static annotation doesn't sneak out
    Function<Stream<? extends AnnotationMirror>, Stream<? extends AnnotationMirror>>
        adjustAnnotations =
            annos -> annos.filter(x -> !"@com.spotify.dataenum.Static".equals(x.toString()));

    return MethodMethods.builderFrom(el, adjustModifiers, adjustAnnotations)
        .addCode(MethodMethods.codeBlockFrom(el, trees))
        .build();
  }
}
