using System;
using System.Activities.Presentation.Metadata;
using System.ComponentModel;
using System.Linq;
using System.Reflection;

namespace ServiceActivities.Design
{
    public sealed class Metadata : IRegisterMetadata
    {
        public const String ServiceActivitiesAssemblyName = "ServiceActivities, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null";

        public void Register()
        {
            AttributeTableBuilder builder = new AttributeTableBuilder();
            var designers = GetType().Assembly.GetTypes();
            var activities = Assembly.Load(new AssemblyName(ServiceActivitiesAssemblyName)).GetTypes();

            // Register designers.
            foreach (var activity in activities)
            {
                var designer = designers.FirstOrDefault(d => d.Name == activity.Name + "Designer");
                if (designer != null)
                    builder.AddCustomAttributes(activity, new DesignerAttribute(designer));
            }

            // Apply the metadata.
            MetadataStore.AddAttributeTable(builder.CreateTable());
        }
    }
}
