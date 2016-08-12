Still going strong?

Good. This section is about UI and creating custom views. Previously you have made
layouts with EditTexts, TextViews, Buttons, etc. Now you are going to build your
own widgets to use with layouts.

Creating widgets has two parts just like any other layout such as an activity
or fragment: the xml layout and the java behind it.

For your first Widget you are going to create a editable text-box with a label
on its left. The background of your widget should be a different color than the
default. The xml for this is the same as any other layout.

The class behind your widget should extend from RelativeLayout (it could probably extend from
  other classes, but I know it works with this one). First thing you need is a constructor that has `Context` and `AttributeSet` parameters. In this constructor you will need to inflate your layout using the `LayoutInflator`:

```java
LayoutInflater inflater = LayoutInflater.from(context);
inflater.inflate(R.layout.your_layout, this);
  ```

Once you have inflated the layout you will need to setup your label. The goal is that someone can use your widget and there would be an attribute that they set to the text for their widget. To set up attributes you need to open `attrs.xml` under values. If it doesn't exist then you should create it. It should look like this:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

  ...

</resources>
```

To add an attribute to a view you will need to make it styleable

```xml
<declare-styleable name="ClassName">
    <attr name="label" format="string" />
</declare-styleable>
```

and retrieve the value assigned to the attribute

```java
TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClassName);
String labelText = typedArray.getString(R.styleable.ClassName_AttributeName);
```

Once you have all this the rest is just tweating the layout to make it look better.
