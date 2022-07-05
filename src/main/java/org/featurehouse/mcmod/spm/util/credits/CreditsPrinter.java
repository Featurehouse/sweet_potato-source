package org.featurehouse.mcmod.spm.util.credits;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;

public record CreditsPrinter(Minecraft client,
                             IntConsumer creditsHeight,
                             IntSet centeredLines,
                             List<FormattedCharSequence> credits) {
    private static final Component SEPARATOR_LINE = new TextComponent("============").withStyle(ChatFormatting.WHITE);
    private static final ResourceLocation SPM_FILE = RegistryHelper.id("credits.json");
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void print(Minecraft client, IntConsumer creditsHeight,
                             IntSet centeredLines, List<FormattedCharSequence> credits) {
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
                    GsonHelper.parse(new BufferedReader(new InputStreamReader(is))),
                    SPM_FILE);

            wrapTitle("Sweet Potato Mod Credits");

            wrapLines(false, "Author Group", ChatFormatting.GRAY);
            renderNameList(modCredits.authorGroup());
            wrapLines(false, "Contributors", ChatFormatting.GRAY);
            renderContributorList(modCredits.contributors());

            wrapTitle("Special Thanks from Sweet Potato Mod");

            wrapLines(false, "Collaborators", ChatFormatting.GRAY);
            renderNameList(modCredits.collaborators());
            wrapLines(false, "Very Important Supporters", ChatFormatting.GRAY);
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
        credits.add(blank().append(string).withStyle(ChatFormatting.WHITE).getVisualOrderText());
    }

    private static TextComponent blank() {
        return new TextComponent("           ");
    }

    private void wrapLines(boolean central, String string, ChatFormatting... fmt) {
        List<FormattedCharSequence> wrapLines = Objects.requireNonNull(client).font.split(
                new TextComponent(string).withStyle(fmt), 274
        );
        for (FormattedCharSequence text : wrapLines) {
            if (central) centeredLines.add(credits.size());
            credits.add(text);
        }
    }

    private void wrapTitle(String string) {
        this.addText(SEPARATOR_LINE, true);
        wrapLines(true, string, ChatFormatting.YELLOW);
        this.addText(SEPARATOR_LINE, true);
        this.addEmptyLine();
        this.addEmptyLine();
    }

    private void renderContributorList(List<ImmutablePair<String, String>> contributors) {
        for (ImmutablePair<String, String> nameAndId : contributors) {
            MutableComponent mutableText = blank().append(new TextComponent(nameAndId.getLeft()).withStyle(ChatFormatting.WHITE));
            if (!nameAndId.getLeft().equals(nameAndId.getRight()))
                mutableText.append(new TextComponent(" (" + nameAndId.getRight() + ')').withStyle(ChatFormatting.GRAY));
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
        this.credits.add(FormattedCharSequence.EMPTY);
    }

    private void addText(Component text, boolean centered) {
        if (centered) {
            this.centeredLines.add(this.credits.size());
        }

        this.credits.add(text.getVisualOrderText());
    }
}
