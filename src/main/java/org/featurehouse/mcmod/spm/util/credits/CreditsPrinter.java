package org.featurehouse.mcmod.spm.util.credits;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.featurehouse.mcmod.spm.util.registries.RegistryHelper;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

public record CreditsPrinter(MinecraftClient client,
                             IntConsumer creditsHeight,
                             IntSet centeredLines,
                             List<OrderedText> credits) {
    private static final Text SEPARATOR_LINE = new LiteralText("============").formatted(Formatting.WHITE);
    private static final Identifier SPM_FILE = RegistryHelper.id("credits.json");
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void print(MinecraftClient client, IntConsumer creditsHeight,
                             IntSet centeredLines, List<OrderedText> credits) {
        new CreditsPrinter(client, creditsHeight, centeredLines, credits)
                .print();
    }

    public void print() {
        Resource resource = null;
        try {
            resource = Objects.requireNonNull(this.client)
                    .getResourceManager()
                    .getResource(SPM_FILE);
            InputStream is = resource.getInputStream();

            ModCredits modCredits = ModCredits.fromJson(
                    JsonHelper.deserialize(new BufferedReader(new InputStreamReader(is))),
                    SPM_FILE);

            wrapTitle("Sweet Potato Mod Credits");

            wrapLines(false, "Author Group", Formatting.GRAY);
            renderNameList(modCredits.authorGroup());
            wrapLines(false, "Contributors", Formatting.GRAY);
            renderContributorList(modCredits.contributors());

            wrapTitle("Special Thanks from Sweet Potato Mod");

            wrapLines(false, "Collaborators", Formatting.GRAY);
            renderNameList(modCredits.collaborators());
            wrapLines(false, "Very Important Supporters", Formatting.GRAY);
            renderNameList(modCredits.importantSupporters());

            is.close();

            this.creditsHeight.accept(this.credits.size() * 12);
        } catch (IOException e) {
            LOGGER.error("Couldn't load credits from Sweet Potato Mod", e);
        } finally {
            IOUtils.closeQuietly(resource);
        }
    }

    private void wrapLines(String string) {
        credits.add(blank().append(string).formatted(Formatting.WHITE).asOrderedText());
    }

    private static LiteralText blank() {
        return new LiteralText("           ");
    }

    private void wrapLines(boolean central, String string, Formatting... fmt) {
        List<OrderedText> wrapLines = Objects.requireNonNull(client).textRenderer.wrapLines(
                new LiteralText(string).formatted(fmt), 274
        );
        for (OrderedText text : wrapLines) {
            if (central) centeredLines.add(credits.size());
            credits.add(text);
        }
    }

    private void wrapTitle(String string) {
        this.addText(SEPARATOR_LINE, true);
        wrapLines(true, string, Formatting.YELLOW);
        this.addText(SEPARATOR_LINE, true);
        this.addEmptyLine();
        this.addEmptyLine();
    }

    private void renderContributorList(List<ImmutablePair<String, String>> contributors) {
        for (ImmutablePair<String, String> nameAndId : contributors) {
            MutableText mutableText = blank().append(new LiteralText(nameAndId.getLeft()).formatted(Formatting.WHITE));
            if (!nameAndId.getLeft().equals(nameAndId.getRight()))
                mutableText.append(new LiteralText(" (" + nameAndId.getRight() + ')').formatted(Formatting.GRAY));
            addText(mutableText, false);
        }
        addEmptyLine();
        addEmptyLine();
    }

    private void renderNameList(List<String> names) {
        for (String name : names) {
            wrapLines(name);
        }
        addEmptyLine();
        addEmptyLine();
    }

    private void addEmptyLine() {
        this.credits.add(OrderedText.EMPTY);
    }

    private void addText(Text text, boolean centered) {
        if (centered) {
            this.centeredLines.add(this.credits.size());
        }

        this.credits.add(text.asOrderedText());
    }
}
