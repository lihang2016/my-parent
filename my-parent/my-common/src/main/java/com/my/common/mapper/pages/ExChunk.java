package com.my.common.mapper.pages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@JsonIgnoreProperties(value = { "pageable","last","first" ,"numberOfElements","sort"})
abstract class ExChunk<T> implements Slice<T>, Serializable {

   private static final long serialVersionUID = 867755909294344406L;

   private final List<T> content = new ArrayList<T>();
   private final Pageable pageable;

   /**
    * Creates a new {@link org.springframework.data.domain.Chunk} with the given content and the given governing {@link Pageable}.
    *
    * @param content must not be {@literal null}.
    * @param pageable can be {@literal null}.
    */
   public ExChunk(List<T> content, Pageable pageable) {

       Assert.notNull(content, "Content must not be null!");

       this.content.addAll(content);
       this.pageable = pageable;
   }

   public void setContent(List<T> content){
       this.content.addAll(content);
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#getNumber()
    */
   public int getNumber() {
       return pageable == null ? 0 : pageable.getPageNumber()+1;
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#getSize()
    */
   public int getSize() {
       return pageable == null ? 0 : pageable.getPageSize();
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#getNumberOfElements()
    */
   public int getNumberOfElements() {
       return content.size();
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#hasPrevious()
    */
   public boolean hasPrevious() {
       return getNumber() > 0;
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#isFirst()
    */
   public boolean isFirst() {
       return !hasPrevious();
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#isLast()
    */
   public boolean isLast() {
       return !hasNext();
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#nextPageable()
    */
   public Pageable nextPageable() {
       return hasNext() ? pageable.next() : null;
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#previousPageable()
    */
   public Pageable previousPageable() {

       if (hasPrevious()) {
           return pageable.previousOrFirst();
       }

       return null;
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#hasContent()
    */
   public boolean hasContent() {
       return !content.isEmpty();
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#getContent()
    */
   public List<T> getContent() {
       return Collections.unmodifiableList(content);
   }

   /*
    * (non-Javadoc)
    * @see org.springframework.data.domain.Slice#getSort()
    */
   public Sort getSort() {
       return pageable == null ? null : pageable.getSort();
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Iterable#iterator()
    */
   public Iterator<T> iterator() {
       return content.iterator();
   }

   /**
    * Applies the given {@link Converter} to the content of the {@link org.springframework.data.domain.Chunk}.
    *
    * @param converter must not be {@literal null}.
    * @return
    */
   protected <S> List<S> getConvertedContent(Converter<? super T, ? extends S> converter) {

       Assert.notNull(converter, "Converter must not be null!");

       List<S> result = new ArrayList<S>(content.size());

       for (T element : this) {
           result.add(converter.convert(element));
       }

       return result;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj) {

       if (this == obj) {
           return true;
       }

       if (!(obj instanceof ExChunk<?>)) {
           return false;
       }

       ExChunk<?> that = (ExChunk<?>) obj;

       boolean contentEqual = this.content.equals(that.content);
       boolean pageableEqual = this.pageable == null ? that.pageable == null : this.pageable.equals(that.pageable);

       return contentEqual && pageableEqual;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {

       int result = 17;

       result += 31 * (pageable == null ? 0 : pageable.hashCode());
       result += 31 * content.hashCode();

       return result;
   }
}
