package li.cil.oc.client.renderer.markdown.segment.render

import com.google.common.base.Strings
import li.cil.oc.OpenComputers
import li.cil.oc.api.manual.ImageProvider
import li.cil.oc.api.manual.ImageRenderer
import li.cil.oc.client.Textures
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

object BlockImageProvider extends ImageProvider {
  override def getImage(data: String): ImageRenderer = {
    val splitIndex = data.lastIndexOf('@')
    val (name, optMeta) = if (splitIndex > 0) data.splitAt(splitIndex) else (data, "")
    val meta = if (Strings.isNullOrEmpty(optMeta)) 0 else Integer.parseInt(optMeta.drop(1))
    Block.blockRegistry.getObject(name) match {
      case block: Block if Item.getItemFromBlock(block) != null => new ItemStackImageRenderer(Array(new ItemStack(block, 1, meta)))
      case _ =>
        OpenComputers.log.warn(s"Failed looking up block '$data'.")
        new TextureImageRenderer(Textures.guiManualMissingItem)
    }
  }
}
