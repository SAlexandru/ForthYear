package infofilter;

import java.beans.*;


/**
 * The <code>NewsReaderAgentBeanInfo</code> class implements bean info for
 * the news reader agent.
 *
 * @author Joseph P. Bigus
 * @author Jennifer Bigus
 *
 * @copyright
 * Constructing Intelligent Agents using Java
 * (C) Joseph P. Bigus and Jennifer Bigus 2001
 *
 */
public class NewsReaderAgentBeanInfo extends SimpleBeanInfo {
  Class<NewsReaderAgent> beanClass = NewsReaderAgent.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;
  private final static Class<NewsReaderAgentCustomizer> customizerClass = infofilter.NewsReaderAgentCustomizer.class;


  /**
   * Creates a <code>NewsReaderAgentBeanInfo</code> object.
   *
   */
  public NewsReaderAgentBeanInfo() {}


  /**
   * Retrieves a descriptor for this bean.
   *
   * @return  a bean descriptor which specifies a customizer for this
   *          bean class
   */
  public BeanDescriptor getBeanDescriptor() {
    BeanDescriptor returnDescriptor = new BeanDescriptor(beanClass, customizerClass);

    returnDescriptor.setValue("DisplayName", "NewsReaderAgent");
    return returnDescriptor;
  }


  /**
   * Retrieves the set of property descriptors for the bean.
   *
   * @return the PropertyDescriptor[] that contains the property descriptors
   *
   */
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _newsHost = new PropertyDescriptor("newsHost", beanClass, "getNewsHost", "setNewsHost");
      PropertyDescriptor _newsGroup = new PropertyDescriptor("newsGroup", beanClass, "getNewsGroup", "setNewsGroup");
      PropertyDescriptor _taskDescription = new PropertyDescriptor("taskDescription", beanClass, "getTaskDescription", null);
      PropertyDescriptor[] pds = new PropertyDescriptor[]{ _newsHost,
                                                           _newsGroup,
                                                           _taskDescription, };

      return pds;
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }


  /**
   * Retrieves the icon.
   *
   * @param iconKind the integer that represents the icon kind
   *
   * @return the java.awt.Image object that contains the icon image
   *
   */
  public java.awt.Image getIcon(int iconKind) {
    switch (iconKind) {
      case BeanInfo.ICON_COLOR_16x16 :
        return (iconColor16x16Filename != null)
               ? loadImage(iconColor16x16Filename)
               : null;
      case BeanInfo.ICON_COLOR_32x32 :
        return (iconColor32x32Filename != null)
               ? loadImage(iconColor32x32Filename)
               : null;
      case BeanInfo.ICON_MONO_16x16 :
        return (iconMono16x16Filename != null)
               ? loadImage(iconMono16x16Filename)
               : null;
      case BeanInfo.ICON_MONO_32x32 :
        return (iconMono32x32Filename != null)
               ? loadImage(iconMono32x32Filename)
               : null;
    }
    return null;
  }


  /**
   * Retrieves additional bean info from the superclass.
   *
   * @return the BeanInfo[] object that contains the additional bean info
   *
   */
  public BeanInfo[] getAdditionalBeanInfo() {
    Class<?> superclass = beanClass.getSuperclass();

    try {
      BeanInfo superBeanInfo = Introspector.getBeanInfo(superclass);

      return new BeanInfo[]{ superBeanInfo };
    } catch (IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
